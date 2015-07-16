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
                <h1 class="">Users</h1>
                <p>Manage your printing services users settings and status.
                </p>

                <h2>Users list</h2>

                <table class="responsive-table">
                    <thead>
                        <tr>
                            <th><abbr title="User Name">Name</abbr></th>
                            <th>Full Name</th>
                            <th><abbr title="Admin User">Admin</abbr></th>
                            <th><abbr title="Valid User">Valid</abbr></th>
                        </tr>
                    </thead>
                    <tbody>
<#list users as user>
<#if user.ValidUser=="1"><#assign ValidUser_class=''><#else><#assign ValidUser_class='grey-text text-lighten-2'></#if>
<#escape user as user?html>                        
                        <tr>
                            <td><a href="${URL_USERS}edit?id=${user.idUser}" class=""><i class="material-icons left ${ValidUser_class}">person</i>${user.UserName}</a></td>
                            <th>${user.FullName}</th>
                            <td><#if user.Role == '1'>Yes<#else>No</#if></td>
                            <td><#if user.ValidUser == '1'>Yes<#else>No</#if></td>
                        </tr>
</#escape>
</#list>

                    </tbody>
                </table>

                <p>&nbsp;</p>
                <a href="${URL_USERS}new" class="btn-floating btn-large green tooltipped" data-tooltip="Add New User" data-position="right"><i class="material-icons left">add</i></a>

            </div>

<#include "part-error.ftl">

        </main>


<#include 'part-footer.ftl'>

            <!-- 
Additional Panels goes here
            -->            

    </body>
</html>
