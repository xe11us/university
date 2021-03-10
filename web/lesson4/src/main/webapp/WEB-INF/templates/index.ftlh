<#-- @ftlvariable name="posts" type="ru.itmo.web.lesson4.model.Post[]" -->
<#import "./common.ftlh" as c>

<@c.page>
    <ul class = "feed">
        <#list posts as data>
            <li>
                <@c.post data/>
            </li>
        </#list>
    </ul>
</@c.page>