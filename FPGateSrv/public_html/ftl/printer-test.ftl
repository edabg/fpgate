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

        <script src="/js/fpg-client.js" type="text/javascript"></script>
        <script src="/js/libs/jquery/jquery.json.min.js" type="text/javascript"></script>
    </head>

    <body class="fixed-header fixed-footer">

<#include 'part-header.ftl'>

<#include 'part-menu.ftl'>

<#if !test??><#assign test = {'Command' : '', 'Parameters' : '', 'Results' : '', 'Errors' : '', 'Log' : ''}></#if>

        <main>
            <div class="container">
                <div class="row">
                    <div class="col s6">
                        <h1 class="">Printer Test</h1>
                    </div>
                    <div class="col s6">
<#if "${idPrinter}" != "" && "${idPrinter}" != "0">
                        <a href="${URL_PRINTERS}edit?id=${idPrinter}" class="btn-floating btn-large green tooltipped right" data-tooltip="Back to Printer Edit" data-position="left">
                            <i class="material-icons left">undo</i>
                        </a>
<#else>
                        <a href="${URL_PRINTERS}" class="btn-floating btn-large green tooltipped right" data-tooltip="Back to Printers List" data-position="left">
                            <i class="material-icons left">undo</i>
                        </a>
</#if>
                    </div>
                    <div class="col s12">
                        <p>Fiscal printer server active printers printing tests.</p>
                    </div>
                </div>

                <form method="post" action="${URL_PRINTERTEST}" name="printer_test" id="printer_test">

<#if "${idPrinter}" != "" && "${idPrinter}" != "0">
                    <input type="hidden" name="Printer.URL" value="${URL_PRINT}">
                    <input type="hidden" name="Printer.ID" value="${printers[idPrinter].RefID}">
<#else>
                    <input type="hidden" name="Printer.URL" value="">
                    <input type="hidden" name="Printer.ID" value="">
</#if>

                    <div class="row">
                        <div class="input-field col m4 s12">
                            <select name="idPrinter" id="printer_model" onChange="this.form.submit();">
                                <option value="">- Printer -</option>
<#list printers?values as printer> 
<#if idPrinter == printer.idPrinter><#assign selected='selected="selected"'><#else><#assign selected=''></#if>
                                <option value="${printer.idPrinter}" ${selected}>${printer.RefID?html!"N/A"}</option>
</#list>
                            </select>
                            <label for="printer_model" class="select">Select Printer</label>
                        </div>
                        <div class="col m8 s12">
                <#if "${idPrinter}" != "" && "${idPrinter}" != "0">
<div class="right">
                            <a href="${URL_PRINTERS}edit?id=${idPrinter}" class="">
<i class="material-icons left">print</i>${printers[idPrinter].Name?html!"N/A"}, ${printers[idPrinter].Location?html!"N/A"}
</a>
    </div>
                </#if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col m4 s12">
                            <select name="Command" id="test_command" onChange="cmdSelect(this);">
                                <option value="">- Command -</option> 
                                <option value="PrinterStatus">Printer Status</option>
                                <option value="PrintFiscalCheck">Print Fiscal Check</option> 
                                <option value="PrintFiscalCheck_Rev_">Print Fiscal Check Rev/Storno</option> 
                                <option value="PrintNonFiscalCheck">Print Non-Fiscal Check</option>
                                <option value="PrintDuplicateCheck">Print Duplicate Check</option>
                                <option value="PrintDuplicateCheckByDocNum">Print Duplicate Check By DocNum</option>
                                <option value="CurrentCheckInfo">Current Check Info</option>
                                <option value="PrintFiscalCheck_Inv_">Print Invoice Fiscal Check</option> 
                                <option value="PrintFiscalCheck_RevInv_">Print Credit Note Fiscal Check</option> 
                                <option value="GetDiagnosticInfo">Get Diagnostic Info</option>
                                <option value="ReportDaily">Report Daily</option> 
                                <option value="ReportByDates">Report By Dates</option> 
                                <option value="LastFiscalRecordInfo">Last Fiscal Record Info</option>
                                <option value="GetDateTime">Get Date Time</option> 
                                <option value="SetDateTime">Set Date Time</option> 
                                <option value="CashInOut">Cash In/Out</option>
                                <option value="CustomCommand">Custom Command</option>
                                <option value="GetJournalInfo">Get Journal Info</option>
                                <option value="GetJournal">Get Journal</option>
                                <option value="GetDocInfo">Get Doc Info</option>
                                <option value="ReadPaymentMethods">Read Payment Methods</option>
                                <option value="GetVersion">Get FPGate Version</option>
                                <option value="AbnormalComplete">Abnormal Complete Fix</option> 
                                <option value="test">Test</option> 
                            </select>                            
                            <label for="test_command" class="select">Select Command</label>
                        </div>
                        <div class="col m4 s6">
                            <a href="javascript: cmdExecute()" class="btn-flat" data-target="modal_delete">Execute
                                <i class="material-icons right">input</i>
                            </a>
                        </div>
                        <div class="col m4 s6">
                            <div class="preloader-wrapper big" id="spinner_execute">
                            <div class="spinner-layer spinner-red-only">
                                <div class="circle-clipper left">
                                    <div class="circle"></div>
                                </div><div class="gap-patch">
                                    <div class="circle"></div>
                                </div><div class="circle-clipper right">
                                    <div class="circle"></div>
                                </div>
                            </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <textarea cols="40" rows="8" name="Parameters" id="test_params" class="materialize-textarea mono" spellcheck="false">${test.Parameters!"N/A"}</textarea>
                            <label for="test_params">Parameters</label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-field col s12">
                            <textarea cols="40" rows="8" name="Result" id="test_result" class="materialize-textarea mono" spellcheck="false">${test.Results!"N/A"}</textarea>
                            <label for="test_result">Result</label>
                        </div>
                        <div class="input-field col s12 red-text">
                            <textarea cols="40" rows="8" name="Errors" id="test_errors" class="materialize-textarea" spellcheck="false">${test.Errors!"N/A"}</textarea>
                            <label for="test_errors">Errors</label>
                        </div>
                        <div class="input-field col s12">
                            <textarea cols="40" rows="8" name="Log" id="test_log" class="materialize-textarea" spellcheck="false">${test.Log!"N/A"}</textarea>
                            <label for="test_log">Log</label>
                        </div>
                    </div>

                </form>
                
            </div>

<#include "part-error.ftl">

        </main>

<#include 'part-footer.ftl'>

    </body>
</html>
