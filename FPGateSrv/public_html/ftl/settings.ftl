<!DOCTYPE html>
<html>
    <head>
        <title>${AppTitleFull}</title>
<#include 'part-head.ftl'>
    </head>

    <body class="fixed-header fixed-footer">

<#include 'part-header.ftl'>

<#include 'part-menu.ftl'>
        <main>
<#include "part-error.ftl">
            <div class="container">
                <h1 class="">Settings</h1>
                <p>Manage your server settings.</p>
                <form method="post" action="${URL_SETTINGS}" name="server_edit" id="server_edit">
                    <div class="row underline">
                        <div class="col s3">
                            <h2 class="section">Server Settings</h2>
                        </div>
                        <div class="col s3">
                            <button type="submit" name="act" value="update-save" class="btn-flat modal-trigger green-text" data-target="modal_update_save">
                                Update&nbsp;&amp;&nbsp;Save<i class="material-icons left">check</i>
                            </button>
                            <button type="submit" name="act" value="update-save" id="btn_update_save" style="display: none;"></button>
                        </div>
                        <div class="col s3">
                            <button type="submit" name="act" value="update" class="btn-flat green-text">
                                Update<i class="material-icons left">check</i>
                            </button>
                        </div>
                        <div class="col s3">
                            <button type="submit" name="act" value="cancel" class="btn-flat">Cancel
                                <i class="material-icons left">undo</i>
                            </button>
                        </div>
                    </div>                    
                    <div class="row">
                        <div class="input-field col s4">
                            <input type="text" class="validate" name="ss.IPAddr" id="ip_addr" value="${ss.IPAddr?html}"/>
                            <label for="ip_addr" >IP Address</label>
                        </div>
                        <div class="col s2">
<#if ss.UseHTTP == 1><#assign checked='checked="checked"'><#else><#assign checked=''></#if>
                            <input value="1" type="checkbox" name="ss.UseHTTP" id="Use_HTTP" ${checked}>
                            <label for="Use_HTTP">HTTP</label>
                        </div>
                        <div class="input-field col s2">
                            <input type="text" class="validate" name="ss.HTTPPort" id="http_port" value="${ss.HTTPPort?html}"/>
                            <label for="http_port" >HTTP Port</label>
                        </div>
                        <div class="col s2">
<#if ss.UseHTTPS == 1><#assign checked='checked="checked"'><#else><#assign checked=''></#if>
                            <input value="1" type="checkbox" name="ss.UseHTTPS" id="Use_HTTPS" ${checked}>
                            <label for="Use_HTTPS">HTTPS</label>
                        </div>
                        <div class="input-field col s2">
                            <input type="text" class="validate" name="ss.HTTPSPort" id="https_port" value="${ss.HTTPSPort?html}"/>
                            <label for="https_port" >HTTPS Port</label>
                        </div>
                        
                        <div class="input-field col s4">
                            <input type="text" class="validate" name="ss.AdminUser" id="user_username" value="${ss.AdminUser?html}"/>
                            <label for="user_username" >Admin User Name</label>
                        </div>

                        <div class="input-field col s4">
                            <input type="password" class="validate" name="ss.UserPass" id="user_password" value=""/>
                            <label for="user_password">Password</label>
                        </div>
                        <div class="input-field col s4">
                            <input type="password" class="validate" name="ss.UserPass2" id="user_password2" value=""/>
                            <label for="user_password2">Confirm Password</label>
                        </div>

                        <div class="col s12">
                            <h2 class="section underline">Printer Pool</h2>
                        </div>
                        <div class="col l2 m4 s6">
<#if ss.PoolEnabled == 1><#assign checked='checked="checked"'><#else><#assign checked=''></#if>
                            <input value="1" type="checkbox" name="ss.PoolEnabled" id="pool_enabled" ${checked}>
                            <label for="pool_enabled">Pool enabled</label>
                        </div>
                        <div class="input-field col l2 m4 s6">
                            <input type="number" class="validate" name="ss.PoolDeadtime" id="pool_deadtime" value="${ss.PoolDeadtime?html}"/>
                            <label for="pool_deadtime" >Pool Deadtime, min</label>
                        </div>
                        <div class="col l8 m4 s12">
                            <button type="submit" name="act" value="clear-pool" class="btn-flat green-text">
                                Clear pool<i class="material-icons left">delete</i>
                            </button>
                        </div>

                    </div>                    
                    <div class="row">
                        <div class="col s12">
                            <h2 class="section underline">Colibri ERP</h2>
                        </div>
                        <div class="col l2 m4 s4">
<#if ss.CoAutoStart == 1><#assign checked='checked="checked"'><#else><#assign checked=''></#if>
                            <input value="1" type="checkbox" name="ss.CoAutoStart" id="coerp_autostart" ${checked}>
                            <label for="coerp_autostart">Auto Start</label>
                        </div>
<#if isCBIOServiceStarted == 1>
<#assign disabled_coerp_start='disabled="disabled"'>
<#assign disabled_coerp_stop=''>
<#else>
<#assign disabled_coerp_start=''>
<#assign disabled_coerp_stop='disabled="disabled"'>
</#if>
                        <div class="col l5 m4 s4">
                            <button type="submit" name="act" value="start-coerp" class="btn-flat green-text" ${disabled_coerp_start}>
                                Start<i class="material-icons left">play_arrow</i>
                            </button>
                        </div>
                        <div class="col l5 m4 s4">
                            <button type="submit" name="act" value="stop-coerp" class="btn-flat green-text" ${disabled_coerp_stop}>
                                Stop<i class="material-icons left">stop</i>
                            </button>
                        </div>

                        <div class="col s12 underline">
                            <h4 class="section ">
                                <span style="float: right; font-size: smaller;">${CBIOServiveState[0]?html}</span>
                                Colibri ERP Link 1
                            </h4>
                        </div>
                        <div class="input-field col s12">
                            <input type="text" class="validate" name="ss.CoURL" id="coERP_URL" value="${ss.CoURL?html}"/>
                            <label for="coERP_URL" >WS URL</label>
                        </div>
                        
                        <div class="input-field col s6">
                            <input type="text" class="validate" name="ss.CoUser" id="coERP_User" value="${ss.CoUser?html}"/>
                            <label for="coERP_User" >User Name</label>
                        </div>

                        <div class="input-field col s6">
                            <input type="password" class="validate" name="ss.CoPass" id="coERP_Pass" value=""/>
                            <label for="coERP_Pass">Password</label>
                        </div>

                        <div class="col s12 underline">
                            <h4 class="section ">
                                <span style="float: right; font-size: smaller;">${CBIOServiveState[1]?html}</span>
                                Colibri ERP Link 2
                            </h4>
                        </div>
                        <div class="input-field col s12">
                            <input type="text" class="validate" name="ss.CoURL_1" id="coERP_URL_1" value="${ss.CoURL_1?html}"/>
                            <label for="coERP_URL_1" >WS URL</label>
                        </div>
                        
                        <div class="input-field col s6">
                            <input type="text" class="validate" name="ss.CoUser_1" id="coERP_User_1" value="${ss.CoUser_1?html}"/>
                            <label for="coERP_User_1" >User Name</label>
                        </div>

                        <div class="input-field col s6">
                            <input type="password" class="validate" name="ss.CoPass_1" id="coERP_Pass_1" value=""/>
                            <label for="coERP_Pass_1">Password</label>
                        </div>

                        <div class="col s12 underline">
                            <h4 class="section ">
                                <span style="float: right; font-size: smaller;">${CBIOServiveState[3]?html}</span>
                                Colibri ERP Link 3
                            </h4>
                        </div>
                        <div class="input-field col s12">
                            <input type="text" class="validate" name="ss.CoURL_2" id="coERP_URL_2" value="${ss.CoURL_2?html}"/>
                            <label for="coERP_URL_2" >WS URL</label>
                        </div>
                        
                        <div class="input-field col s6">
                            <input type="text" class="validate" name="ss.CoUser_2" id="coERP_User_2" value="${ss.CoUser_2?html}"/>
                            <label for="coERP_User_2" >User Name</label>
                        </div>

                        <div class="input-field col s6">
                            <input type="password" class="validate" name="ss.CoPass_2" id="coERP_Pass_2" value=""/>
                            <label for="coERP_Pass_2">Password</label>
                        </div>

                        <div class="col s12 underline">
                            <h4 class="section ">
                                <span style="float: right; font-size: smaller;">${CBIOServiveState[3]?html}</span>
                                Colibri ERP Link 4
                            </h4>
                        </div>
                        <div class="input-field col s12">
                            <input type="text" class="validate" name="ss.CoURL_3" id="coERP_URL_3" value="${ss.CoURL_3?html}"/>
                            <label for="coERP_URL_3" >WS URL</label>
                        </div>
                        
                        <div class="input-field col s6">
                            <input type="text" class="validate" name="ss.CoUser_3" id="coERP_User_3" value="${ss.CoUser_3?html}"/>
                            <label for="coERP_User_3" >User Name</label>
                        </div>

                        <div class="input-field col s6">
                            <input type="password" class="validate" name="ss.CoPass_3" id="coERP_Pass_3" value=""/>
                            <label for="coERP_Pass_3">Password</label>
                        </div>
                    </div>                    

                    <div class="row">
                        <div class="col s12">
                            <h2 class="section underline">Log Levels</h2>
                        </div>
                        <div class="input-field col s6">
                            <select name="ss.LLProtocol" id="LLProtocol" title="Log level for protocols">
<#list ss.LogLevelsList as level>
<#if level == ss.LLProtocol><#assign selected='selected="selected"'><#else><#assign selected=''></#if>
                                <option value="${level?html}" ${selected}>${level?html}</option>
</#list>
                            </select>
                            <label for="LLProtocol" class="select">Protocol</label>
                        </div>
                        <div class="input-field col s6">
                            <select name="ss.LLDevice" id="LLDevice" title="Log level for protocols">
<#list ss.LogLevelsList as level>
<#if level == ss.LLDevice><#assign selected='selected="selected"'><#else><#assign selected=''></#if>
                                <option value="${level?html}" ${selected}>${level?html}</option>
</#list>
                            </select>
                            <label for="LLDevice" class="select">Device low level</label>
                        </div>
                        <div class="input-field col s6">
                            <select name="ss.LLFPCBase" id="LLFPCBase" title="Log level for protocols">
<#list ss.LogLevelsList as level>
<#if level == ss.LLFPCBase><#assign selected='selected="selected"'><#else><#assign selected=''></#if>
                                <option value="${level?html}" ${selected}>${level?html}</option>
</#list>
                            </select>
                            <label for="LLFPCBase" class="select">Device app level</label>
                        </div>
                        <div class="input-field col s6">
                            <select name="ss.LLCBIOService" id="LLCBIOService" title="Log level for protocols">
<#list ss.LogLevelsList as level>
<#if level == ss.LLCBIOService><#assign selected='selected="selected"'><#else><#assign selected=''></#if>
                                <option value="${level?html}" ${selected}>${level?html}</option>
</#list>
                            </select>
                            <label for="LLCBIOService" class="select">Colibri ERP</label>
                        </div>

                    </div>                    
                    <div class="row">
                        <div class="col s12">
                            <h2 class="section underline">Tremol ZFPLab Server</h2>
                        </div>
                        <div class="col l2 m4 s4">
<#if ss.ZFPLabServerAutoStart == 1><#assign checked='checked="checked"'><#else><#assign checked=''></#if>
                            <input value="1" type="checkbox" name="ss.ZFPLabServerAutoStart" id="ZFPLabServerAutoStart" ${checked}>
                            <label for="ZFPLabServerAutoStart">Auto Start</label>
                        </div>
<#if ZFPLabServerStarted == 1>
<#assign disabled_zfp_start='disabled="disabled"'>
<#assign disabled_zfp_stop=''>
<#else>
<#assign disabled_zfp_start=''>
<#assign disabled_zfp_stop='disabled="disabled"'>
</#if>
                        <div class="col l5 m4 s4">
                            <button type="submit" name="act" value="start-zfplabserver" class="btn-flat green-text" ${disabled_zfp_start}>
                                Start<i class="material-icons left">play_arrow</i>
                            </button>
                        </div>
                        <div class="col l5 m4 s4">
                            <button type="submit" name="act" value="stop-zfplabserver" class="btn-flat green-text" ${disabled_zfp_stop}>
                                Stop<i class="material-icons left">stop</i>
                            </button>
                        </div>
                    </div>                    
                    <div class="row underline">
                        <div class="col s12">
                            <h2 class="section underline">Reset to defaults</h2>
                        </div>
                        <div class="col s12">
                            <button type="submit" name="act" value="reset-defaults" class="btn-flat modal-trigger green-text" data-target="modal_reset">
                                Reset<i class="material-icons left">settings_backup_restore</i>
                            </button>
                        </div>
                    </div>                    
                    <div id="modal_update_save" class="modal">
                        <div class="modal-content">
                            <h2>Update and save settings</h2>
                            <p>Are you sure you want to update setting and save permanently?</p>
                            <p>This action cannot be undone.</p>
                        </div>
                        <div class="modal-footer">
                            <a href="javascript: document.getElementById('btn_update_save').click();" class="btn-flat red-text">Save<i class="material-icons left">save</i></a>
                            <a href="#!" class="modal-action modal-close btn-flat">Cancel<i class="material-icons left">undo</i></a>
                        </div>
                    </div>
                    <div id="modal_reset" class="modal">
                        <div class="modal-content">
                            <h2>Reset settings to defaults</h2>
                            <p>Are you sure you want to reset settings to defaults?</p>
                            <p>This action cannot be undone.</p>
                        </div>
                        <div class="modal-footer">
                            <a href="${URL_SETTINGS}reset-defaults" class="btn-flat red-text">Reset<i class="material-icons left">settings_backup_restore</i></a>
                            <a href="#!" class="modal-action modal-close btn-flat">Cancel<i class="material-icons left">undo</i></a>
                        </div>
                    </div>
                </form>
            </div>
        </main>

<#include 'part-footer.ftl'>

        <!--  Scripts-->
    </body>

</html>
