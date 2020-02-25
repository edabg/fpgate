<#if errors?size gt 0 || warnings?size gt 0 || messages?size gt 0>
    <div class="container">  
        <ul class="collapsible" data-collapsible="accordion">
<#list errors as err>                    
<#escape err as err?html>                        
            <li>
                <div class="collapsible-header red white-text active"><i class="material-icons">error</i>Error!</div>
                <div class="collapsible-body"><p>${err}</p></div>
            </li>
</#escape>
</#list>                    
<#list warnings as warn>                    
<#escape warn as warn?html>                        
            <li>
                <div class="collapsible-header orange white-text"><i class="material-icons">warning</i>Warning!</div>
                <div class="collapsible-body"><p>${warn}</p></div>
            </li>
</#escape>
</#list>                    
<#list messages as msg>                    
<#escape msg as msg?html>                        
            <li>
                <div class="collapsible-header green white-text"><i class="material-icons">info</i>Info!</div>
                <div class="collapsible-body"><p>${msg}</p></div>
            </li>
</#escape>
</#list>                    
        </ul>
    </div>
</#if>