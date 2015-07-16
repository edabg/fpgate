<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>${AppTitleFull}</title>
<#include 'part-head.ftl'>
    </head>

    <body class="fixed-header fixed-footer">

<#include 'part-header.ftl'>

<#include 'part-menu.ftl'>

        <main>
            <div class="container">
                <h1 class="">EDA<sup>&copy;</sup> FPGate Server</h1>
                <p>EDA<sup>&copy;</sup> FPGate Server is a dedicated printer server for fiscal printers.</p>
                <p>&nbsp;</p>

            </div>

            <div class="container">
                <div class="section">

                    <!--   Icon Section   -->
                    <div class="row">
                        <div class="col s12 m4">
                            <div class="">
                                <h2 class="center"><i class="medium material-icons">printer</i></h2>
                                <h3 class="center">Printers</h3>

                                <p>Manage your connected printers and their settings and status.
                                    <br />Currently there is/are ${PrinterCountAll} connected printer/s and ${PrinterCountActive} of which is/are active.
                                </p>
                            </div>
                        </div>

                        <div class="col s12 m4">
                            <div class="">
                                <h2 class="center"><i class="medium material-icons">group</i></h2>
                                <h3 class="center">Users</h3>

                                <p>Manage your printing services users settings and status.
                                    <br />Currently there is/are ${UserCountAll} user/s and ${UserCountValid} of which is/are active.
                                </p>
                            </div>
                        </div>

                        <div class="col s12 m4">
                            <div class="">
                                <h2 class="center"><i class="medium material-icons">info_outline</i></h2>
                                <h3 class="center">Status</h3>
                                <p>Manage FPGate Server status.
                                    <br />Fiscal printer server is Up and running.
                                </p>
                            </div>
                        </div>
                    </div>

                </div>
                <p>&nbsp;</p>

            </div>

<#include "part-error.ftl">

        </main>



<#include 'part-footer.ftl'>

        <!--  Scripts-->
    </body>
</html>
