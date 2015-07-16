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
                <h1 class="">Status</h1>
                <p>Manage FPGate Server status.</p>

                <div class="row">
                    <div class="col m6 s12 center">
                        <h3>Printer Server Status</h3>
                        <p>Fiscal printer server is Up and running..</p>
                    </div>
                    <div class="col m6 s12 center">
                        <h3 class="">Printer Server Test</h3>
                        <p>Fiscal printer server active printers printing tests.</p>
                        <a href="${URL_PRINTERTEST}" class="btn-flat">Test a printer
                            <i class="material-icons left">replay</i>
                        </a>
                    </div>
                </div>

            </div>

<#include "part-error.ftl">

        </main>

<#include 'part-footer.ftl'>

    </body>
</html>
