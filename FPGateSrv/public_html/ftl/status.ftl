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
            <div class="container">
                <h1 class="">Status</h1>
                <p>FPGate Server Version: ${AppVersion?html} Build ${AppBuild?html}.</p>
                <div class="row">
                    <div class="col m6 s12 left">
                        <h3>Printer Server Status</h3>
                        <p>Fiscal printer server is Up and running ${ServerUptime}.</p>
<#if PrinterPoolEnabled == 1>
                        <p>Printer pool is enabled. Size = ${PrinterPoolSize}</p>
<#else>
                        <p>Printer pool is disabled.</p>
</#if>
<#if isCBIOServiceStarted == 1>
<div>
Colibri ERP connector is started.
<ol>
<#list CBIOServiveState as state>
<li>${state?html}</li>
</#list>
<ol>
</div>
<#else>
<p>Colibri ERP connector is stopped.</p>
</#if>
<#if ZFPLabServerStarted == 1>
<p>Tremol ZFPLabServer is started.</p>
</#if>
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
