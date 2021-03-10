<#import "./common.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Handle</th>
                <th>Id</th>
            </tr>
            </thead>
            <tbody>
                <#list users as u>
                    <tr>
                    <th>
                        <#if user?? && user.id == u.id>*</#if>
                        <@c.userlink u u.name false/>
                    </th>
                    <th><@c.userlink u u.handle false/></th>
                    <th>${u.id}</th>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</@c.page>
