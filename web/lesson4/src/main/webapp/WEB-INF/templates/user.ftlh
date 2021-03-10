<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->

<#import "./common.ftlh" as c>

<@c.page>
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#else>
        <#if user_id??>
            <#assign u = c.findBy(users, "id", user_id)!/>
        </#if>
    </#if>

    <#if u?has_content>
        <div class="userbox">
            <div class="user-title-photo">
                <img src="//userpic.codeforces.com/no-title.jpg" alt="There was profile picture">
            </div>
            <div class="main-info">
                <h1>
                    <@c.userlink u u.name false/>
                </h1>
                <ul>
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                </ul>
            </div>
        </div>
        <div class="user-switcher">
            <@arrows u/>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>

<#macro arrows user>
    <#assign index = users?seq_index_of(user)>
    <#if index != 0>
        <a href="/user?handle=${users[index - 1].handle}" style="text-decoration: none;">←</a>
    <#else>
        ←
    </#if>

    <#if index != (users?size - 1)>
        <a href="/user?handle=${users[index + 1].handle}" style="text-decoration: none;">→</a>
    <#else>
        →
    </#if>
</#macro>
