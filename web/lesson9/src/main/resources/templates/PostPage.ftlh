<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <div class="body">
            <@c.post post false/>
            <form method="post" action="">
                <div class="comment-field">
                    <div class="name">
                        <label for="text">Write your comment:</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text">${comment.text!}</textarea>
                    </div>
                    <@c.error "comment.text"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Write">
                </div>
            </form>
            <#list post.comments as com>
                <table class="comment">
                    <tbody>
                    <tr>
                        <th class="login">${com.user.login}</th>
                        <th>${com.text}</th>
                    </tr>
                    </tbody>
                </table>
            </#list>
        </div>
    <#else>
        No such post
    </#if>
</@c.page>