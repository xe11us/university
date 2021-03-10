<#-- @ftlvariable name="profile" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if profile??>
        <div class="userbox">
            <div class="user-title-photo">
                <img src="//userpic.codeforces.com/no-title.jpg" alt="There was profile picture">
            </div>
            <div class="main-info">
                <h1>
                    ${profile.login}
                </h1>
                <ul>
                    <li>id: ${profile.id}</li>
                    <li>creationTime: ${profile.creationTime}</li>
                </ul>
            </div>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
