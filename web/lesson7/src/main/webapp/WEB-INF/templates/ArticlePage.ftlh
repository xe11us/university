<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="article form-box">
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="post"/>
                <div class="field">
                    <div class="name">
                        Title:
                    </div>
                    <div class="value">
                        <input id="title" name="title" value="${title!}">
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text:</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text"></textarea>
                    </div>
                </div>
                <div class="error">
                    ${error!}
                </div>
                <div class="button-field">
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".article form").submit(function () {
                const title = $(this).find("input[name='title']").val();
                const text = $(this).find("textarea[name='text']").val();
                const $error = $(this).find(".error");

                ajax({
                        action: "post",
                        title: title,
                        text: text
                    },
                    function (response) {
                        if (response["error"]) {
                            $error.text(response["error"]);
                        }
                    });
                return false;
            });
        })
    </script>
</@c.page>