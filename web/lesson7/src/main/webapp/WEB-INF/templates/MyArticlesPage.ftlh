<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="my-articles datatable">
        <div class="caption">My articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hide/Show</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as article>
                <tr>
                    <td>
                        ${article.id}
                    </td>
                    <td>
                        ${article.title}
                    </td>
                    <td>
                        <input class="button" data-id="${article.id}" type="submit"
                               value="${article.hidden?then('Show', 'Hide')}"/>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(".my-articles .button").click(function () {
            const $button = $(this);
            ajax(
                {
                    action: "changeHiddenState",
                    id: $(this).attr("data-id"),
                    hidden: ($button.val() === "Show")
                },
                function (response) {
                    if (response["error"]) {
                        notify(response["error"], "error");
                        return;
                    }
                    if ($button.val() === "Hide") {
                        $button.val("Show");
                    } else {
                        $button.val("Hide");
                    }
                }
            )
        });
    </script>
</@c.page>
