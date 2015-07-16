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
<#escape user as user?html>                        
            <div class="container">
                <div class="row">
                    <div class="col s6">
                        <h1 class="">User</h1>
                    </div>
                    <div class="col s6 fixed">
                        <a href="${URL_USERS}" class="btn-floating btn-large green tooltipped right" data-tooltip="Back to Users List" data-position="left">
                            <i class="material-icons left">undo</i>
                        </a>
                    </div>
                </div>

                <form method="post" action="${URL_USERS}edit" name="user_edit" id="user_edit">
                    <input type="hidden" name="idUser" value="${user.idUser}" />

                    <div class="row">
                        <div class="col s12">
                            <h2>User data</h2>
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-field col s6">
                            <input type="text" class="validate" name="UserName" id="user_username" value="${user.UserName}"/>
                            <label for="user_username" >UserName</label>
                        </div>
                        <div class="input-field col s6">
                            <input type="text" class="validate" name="FullName" id="printer_name" value="${user.FullName}"/>
                            <label for="user_fullname">Full Name</label>
                        </div>

                        <div class="input-field col s6">
                            <input type="password" class="validate" name="UserPass" id="user_password" value=""/>
                            <label for="user_password">Password</label>
                        </div>
                        <div class="input-field col s6">
                            <input type="password" class="validate" name="UserPass2" id="user_password2" value=""/>
                            <label for="user_password2">Confirm Password</label>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col s6">
<#if "1" == user.ValidUser><#assign checked='checked="checked"'><#else><#assign checked=''></#if>
                            <input value="1" type="checkbox" name="ValidUser" id="user_valid" ${checked}>
                            <label for="user_valid">Valid User</label>
                        </div>
                        <div class="col s6">
<#if "1" == user.Role><#assign checked='checked="checked"'><#else><#assign checked=''></#if>
                            <input value="1" type="checkbox" name="Role" id="user_admin" ${checked}>
                            <label for="user_admin">Administrator</label>
                        </div>
                    </div>
                    <p>&nbsp;</p>

                    <div class="row">
                        <div class="col m4 s12">
                            <button type="submit" name="act" value="update" class="btn-flat green-text">
<#if user.idUser != '0'>
                                Save<i class="material-icons left">check</i>
<#else>
                                Add User<i class="material-icons left">add</i>
</#if>
                            </button>
                        </div>
                        <div class="col m4 s12">
                            <button type="submit" name="act" value="cancel" class="btn-flat">Cancel
                                <i class="material-icons left">undo</i>
                            </button>
                        </div>
<#if user.idUser != '0'>
                        <div class="col m4 s12">
                            <a href="#modal_delete" class="btn-flat modal-trigger" data-target="modal_delete">Delete
                                <i class="material-icons left">clear</i>
                            </a>
                        </div>
</#if>
                    </div>                    
<!-- Modal Structure -->
<#if user.idUser != '0'>
                    <div id="modal_delete" class="modal">
                        <div class="modal-content">
                            <h2>Delete User</h2>
                            <p>Are you sure you want to delete this user?</p>
                            <p>This action cannot be undone.</p>
                        </div>
                        <div class="modal-footer">
                            <a href="${URL_USERS}delete?id=${user.idUser}" class="btn-flat red-text">Delete<i class="material-icons left">clear</i></a>
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

    </body>
</html>
