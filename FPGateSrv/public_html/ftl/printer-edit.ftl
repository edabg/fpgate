<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
Freemaker template, http://freemarker.org/
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
<#escape printer as printer?html>                        
            <div class="container">
                <div class="row">
                    <div class="col s6">
                        <h1 class="">Printer</h1>
                    </div>
                    <div class="col s6">
                        <a href="${URL_PRINTERS}" class="btn-floating btn-large green tooltipped right" data-tooltip="Back to Printers List" data-position="left">
                            <i class="material-icons left">undo</i>
                        </a>
                    </div>
                </div>

                <form method="post" action="${URL_PRINTERS}edit" name="printer_edit" id="printer_edit">
                    <input type="hidden" name="idPrinter" value="${printer.idPrinter}" />

                    <div class="row">
                        <div class="col s12">
                            <h2 class="section underline">Printer data</h2>
                        </div>

                        <div class="input-field col s6">
                            <input type="text" class="validate" name="RefID" id="printer_id" value="${printer.RefID}"/>
                            <label for="printer_id" >Printer ID</label>
                        </div>
                        <div class="input-field col s6">
                            <input type="text" class="validate" name="Name" id="printer_name" value="${printer.Name}"/>
                            <label for="printer_name">Name</label>
                        </div>
                        <div class="input-field col s6">
                            <select name="ModelID" id="printer_model" onChange="this.form.submit();" title="Ala bala">
                                <option value="">- Model -</option>
<#list FPCList as model>
<#if "${model}"=="${printer.ModelID}"><#assign selected='selected="selected"'><#else><#assign selected=''></#if>
                                <option value="${model}" label="label" ${selected}>${model}</option>
</#list>
                            </select>
                            <label for="printer_model" class="select">Model</label>
<#if '' != printer.ModelDescription>
                            <span>${printer.ModelDescription}<span>        
</#if>
                        </div>
                        <div class="input-field col s6">
                            <input type="text" class="validate" name="Location" id="printer_place" value="${printer.Location}"/>
                            <label for="printer_place">Place</label>
                        </div>
                        <div class="input-field col s12">
                            <textarea cols="40" rows="8" name="Description" id="printer_description" class="materialize-textarea">${printer.Description}</textarea>
                            <label for="printer_description">Description</label>
                        </div>
                        <div class="input-field col s6">
<#if "1" == printer.IsActive><#assign checked='checked="checked"'><#else><#assign checked=''></#if>
                            <div class="switch">
                                <label>
                                    No
                                    <input value="1" type="checkbox" name="IsActive" id="printer_active" ${checked}>
                                    <span class="lever"></span>
                                    Active
                                </label>
                            </div>
                        </div>
                        <div class="input-field col s6">
<#if printer.idPrinter == '0' || printer.IsActive == '0'>
                            <a href="!#" class="btn-flat right disabled">Test printer
                                <i class="material-icons left">replay</i>
                            </a>
<#else>
                            <a href="${URL_PRINTERTEST}?id=${printer.idPrinter}" class="btn-flat right">Test printer
                                <i class="material-icons left">replay</i>
                            </a>
</#if>
                        </div>
                        <p>&nbsp;</p>
                    </div>

<#list ParamGroups as PGroup>
                    <div class="row">
                        <div class="col s12">
                            <h2 class="section underline">${PGroup.Name}</h2>
                        </div>
<#list PGroup.Properties as Prop>
                        <div class="input-field col s6">
<#if Prop.RuleApply == '1'>
<#if Prop.RuleListDefined == '1'>
                            <select name="Property[${Prop.ID}]" id="prop_${Prop.ID}">
<#list Prop.RuleList?keys as lkey>
<#if lkey == Prop.Value>selected="selected"<#assign selected='selected="selected"'><#else><#assign selected=''></#if>
                                <option value="${lkey}" ${selected}>${Prop.RuleList[lkey]}</option>
</#list>
                            </select>
<#else>
                            <input type="<#if Prop.IsNumber == '1'>number<#else>text</#if>" name="Property[${Prop.ID}]" id="prop_${Prop.ID}" value="${Prop.Value}"/>
</#if>                    
<#else>                        
                            <input type="<#if Prop.IsNumber == '1'>number<#else>text</#if>" name="Property[${Prop.ID}]" id="prop_${Prop.ID}" value="${Prop.Value}"/>
</#if>                        
                            <label for="prop_${Prop.ID}">${Prop.Name}</label>
                        </div>
</#list>
                        <p>&nbsp;</p>
                    </div>
</#list>

                    <div class="row">
                        <div class="col m4 s12">
                            <button type="submit" name="act" value="update" class="btn-flat green-text">
<#if printer.idPrinter != '0'>
                                Save<i class="material-icons left">check</i>
<#else>
                                Add Printer<i class="material-icons left">add</i>
</#if>
                            </button>
                        </div>
                        <div class="col m4 s12">
                            <button type="submit" name="act" value="cancel" class="btn-flat">Cancel
                                <i class="material-icons left">undo</i>
                            </button>
                        </div>
<#if printer.idPrinter != '0'>
                        <div class="col m4 s12">
                            <a href="#modal_delete" class="btn-flat modal-trigger" data-target="modal_delete">Delete
                                <i class="material-icons left">clear</i>
                            </a>
                        </div>
</#if>
                    </div>                    

<!-- Modal Structure -->
<#if printer.idPrinter != '0'>
                    <div id="modal_delete" class="modal">
                        <div class="modal-content">
                            <h2>Delete Printer</h2>
                            <p>Are you sure you want to delete this printer?</p>
                            <p>This action cannot be undone.</p>
                        </div>
                        <div class="modal-footer">
                            <a href="${URL_PRINTERS}delete?id=${printer.idPrinter}" class="btn-flat red-text">Delete<i class="material-icons left">clear</i></a>
                            <a href="#!" class="modal-action modal-close btn-flat">Cancel<i class="material-icons left">undo</i></a>
                        </div>
                    </div>
</#if>
                </form>

            </div>
</#escape>

<#include "part-error.ftl">

        </main>

<#include 'part-footer.ftl'>

<!-- 
Additional Panels goes here
            -->            

        </div><!-- page -->
    </body>
</html>