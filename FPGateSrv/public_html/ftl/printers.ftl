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
                <h1 class="">Printers</h1>
                <p>Manage your connected printers and their settings and status.
<!--                    <br />Currently there is/are {PrinterCountAll} connected printer/s and {PrinterCountActive} of which is/are active. -->
                </p>

                <h2 class="section underline">Printer list</h2>

                <table class="responsive-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th><abbr title="Printer Name">Name</abbr></th>
                            <th><abbr title="Printer Model">Model</abbr></th>
                            <th><abbr title="Printer Description">Description</abbr></th>
                            <th>Place</th>
                        </tr>
                    </thead>
                    <tbody>
<#list printers as printer>
<#escape printer as printer?html>                        
    <tr>
        <th>${printer.RefID}</td>
        <td><a href="${URL_PRINTERS}edit?id=${printer.idPrinter}" class=""><i class="material-icons left ${printer.IsActive?switch("1","","grey-text text-lighten-2")}">print</i>${printer.Name?switch('','N/A',printer.Name)}</a></td>
        <td>${printer.ModelID}</td>
        <td>${printer.Description}</td>
        <td>${printer.Location}</td>
    </tr>
</#escape>
</#list>
                    </tbody>
                </table>

                <p>&nbsp;</p>
                <a href="${URL_PRINTERS}new" class="btn-floating btn-large green tooltipped" data-tooltip="Add New Printer" data-position="right"><i class="material-icons left">add</i></a>

            </div>

<#include "part-error.ftl">

        </main>

<#include 'part-footer.ftl'>

        <!--  Scripts-->
    </body>

</html>
