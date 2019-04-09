/*
 * Copyright (C) 2019 EDA Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.eda.fpsrv;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Comparator;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eda.fdevice.FPCBase;
import org.restlet.Request;
import org.restlet.data.Header;
import org.restlet.data.MediaType;
import org.restlet.data.Reference;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;

/**
 *
 * @author Dimitar Angelov
 */
public class AppUpdateChecker {
    protected static final Logger logger = Logger.getLogger(FPCBase.class.getName());
    
    protected static String APIURL = "https://api.github.com/repos/edabg/fpgate/";

    public static class BuildComparator implements Comparator<String> {

        @Override
        public int compare(String localBuild, String remoteBuild) {
            long local = 0l;
            long remote = 0l;
            if (localBuild.equals("N/A") || remoteBuild.equals("N/A")) return 0; // can't compare
            if ((localBuild.length() == 0) || (remoteBuild.length() == 0)) return 0; // can't compare
            try {
                local = Long.parseLong(localBuild);
                remote = Long.parseLong(remoteBuild);
            } catch (Exception e) {
            }
            if (local < remote)
                return -1;
            else if (local > remote)
                return +1;
            else 
                return 0;
        }
    }    
    

    /**
     *
     * @author Dino Tsoumakis
     */    
    public static class VersionComparator implements Comparator<String> {

        private static final String VERSION_SEPARATOR = "\\.";
        private static final String QUALIFIER_SEPARATOR = "-";

        @Override
        public int compare(String localVersion, String remoteVersion) {
            String local = null;
            String qualifierLocal = null;
            String remote = null;
            String qualifierRemote = null;


            int qualifierIndexLocal = localVersion.indexOf(QUALIFIER_SEPARATOR);
            int qualifierIndexRemote = remoteVersion.indexOf(QUALIFIER_SEPARATOR);

            if (qualifierIndexLocal > 0) {
                qualifierLocal = localVersion.substring( qualifierIndexLocal + 1 );
                local = localVersion.substring(0, qualifierIndexLocal);
            } else {
                local = localVersion;
            }

            if (qualifierIndexRemote > 0) {
                qualifierRemote = remoteVersion.substring(qualifierIndexRemote + 1);
                remote = remoteVersion.substring(0, qualifierIndexRemote);
            } else {
                remote = remoteVersion;
            }

            String[] p1 = local.split(VERSION_SEPARATOR);
            String[] p2 = remote.split(VERSION_SEPARATOR);

            int n = Math.min(p1.length, p2.length);
            for (int i = 0; i < n; i++) {
                try {
                    int a1 = Integer.parseInt(p1[i]);
                    int a2 = Integer.parseInt(p2[i]);
                    if (a1 < a2) {
                        return -1;
                    } else if (a1 > a2) {
                        return 1;
                    }
                } catch (NumberFormatException nfe) {
                    // Handle NaN errors
                }
            }

            // different length
            if (Math.max(p1.length, p2.length) > n) {
                if (p1.length > p2.length) {
                    return 1;
                } else if (p1.length < p2.length) {
                    return -1;
                }
            }

            if (qualifierLocal != null) {
                if (qualifierRemote == null || qualifierRemote.isEmpty()) {
                    // Version without qualifiers always win
                    return -1;
                }
            } else {
                if (qualifierRemote != null) {
                    // Versions without qualifiery always win
                    return 1;
                }
            }

            return 0;
        }
    }    
    
    public static enum VersionState {
        NEW_VERSION, NEW_BUILD, MATCH, OLD_VERSION, OLD_BUILD, NA
    }
    
    public static class VersionInformation {
        String localVersion = "";
        String localBuild = "";
        String remoteVersion = "";
        String remoteBuild = "";
        String remoteVersionDownloadURL = "";
        long   remoteVersionDownloadSize = 0;
        String remoteVersionInfo = "";
        VersionState state = VersionState.NA;
    }
    
    public static VersionInformation CheckForNewVersion(String localVersion, String localBuild) {
        VersionInformation vi = new VersionInformation();
        vi.localVersion = localVersion;
        vi.localBuild = localBuild;
        int compare = 0;
        try {
            logger.info("Checking for new app version (current:"+localVersion+")");
    //        Client client = new Client(Protocol.HTTPS);
            Reference apiUri = new Reference(APIURL+"releases/latest");
            ClientResource cr = new ClientResource(apiUri);
            Request req = cr.getRequest();
            // now header
            Series<Header> headerValue = new Series<Header>(Header.class);
            req.getAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, headerValue);
            headerValue.add("Accept", "application/json");
            headerValue.add("Content-Type", "application/json");
            // fire it up
            cr.get(MediaType.APPLICATION_JSON);
            Representation output = cr.getResponseEntity();   
            ObjectMapper mapper=new ObjectMapper();
            JsonNode rootNode = mapper.readTree(output.getStream());
            vi.remoteVersion = rootNode.get("tag_name").asText();
            vi.remoteVersionInfo = rootNode.get("body").asText();
            Iterator<JsonNode> assets = rootNode.get("assets").elements();
            String fileName = "";
            while (assets.hasNext()){
                JsonNode asset = assets.next();
                //TODO: Check Asset name to match name convention!
                String name = asset.get("name").asText();
                if (name.matches(".+[.]jar$")) {
                    vi.remoteVersionDownloadURL = (asset).get("browser_download_url").asText();
                    fileName = name;
                    vi.remoteVersionDownloadSize = (asset).get("size").asInt();
                    break;
                }    
            }    
            if (vi.remoteVersion.substring(0,1).equals("v"))
                vi.remoteVersion = vi.remoteVersion.substring(1);
            
            // Determine build from file name
            Pattern buildPattern = Pattern.compile("[_]([0-9]+)[.]jar");
            Matcher buildMatcher = buildPattern.matcher(fileName);
            if (buildMatcher.find()) 
                vi.remoteBuild = buildMatcher.group(1);
            logger.fine("latest version:"+vi.remoteVersion);
            logger.fine("versionDescription:"+vi.remoteVersionInfo);
            logger.fine("versionDownloadURL:"+vi.remoteVersionDownloadURL);
            VersionComparator vc = new VersionComparator();
            compare = vc.compare(vi.localVersion, vi.remoteVersion);
            if (compare == 0) {
                logger.info("There is no new version.");
                vi.state = VersionState.MATCH;
                BuildComparator bc = new BuildComparator();
                compare = bc.compare(vi.localBuild, vi.remoteBuild);
                if (compare == 0) {
                    logger.info("There is no new build.");
                    // vi.state = VersionState.MATCH;
                } else if (compare < 0) {
                    logger.info("There is new build.");
                    vi.state = VersionState.NEW_BUILD;
                } else {
                    logger.info("Remote build is older than local one.");
                    vi.state = VersionState.OLD_BUILD;
                }
            } if (compare < 0) {
                logger.info("There is new version.");
                vi.state = VersionState.NEW_VERSION;
            } else {
                logger.info("Remote version is older than local one.");
                vi.state = VersionState.OLD_VERSION;
            }
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            
        }
        return vi;
    }
}
