<#import "commons.ftlh" as c>

<@c.page>
    Welcome on Codeforces!

    <div class="articles">
        <div class="list">
            <ul>
                <li class="noData">No data</li>
            </ul>
        </div>
        <template>
            <li>
                <article>
                    <div class="title">Title...</div>
                    <div class="information">Info...</div>
                    <div class="text">
                        Text...
                    </div>
                    <ul class="attachment">
                        <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                        <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
                    </ul>
                    <div class="footer">
                        <div class="left">
                            <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                            <span class="positive-score">+173</span>
                            <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                        </div>
                        <div class="right">
                            <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                            <time class="creationTime"></time>
                            <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                            <a href="#">68</a>
                        </div>
                    </div>
                </article>
            </li>
        </template>
    </div>

    <script>
        $(function () {
            const $articles = $(".articles");
            const $noData = $articles.find(".noData");

            $noData.text("Loading...");

            $("aside section").click(function () {
                $(this).css("background-color", "red");
            });

            ajax({
                action: "findAll"
            }, function (response) {
                $noData.remove();

                const users = response["usersMap"];
                response["articles"].forEach(function (article) {
                    let $tpl = $($articles.find("template").prop("content")).clone();
                    $tpl.find(".title").text(article["title"]);
                    $tpl.find(".information").text("By " + users[article["userId"]]["login"]);
                    $tpl.find(".text").text(article["text"]);
                    $tpl.find(".creationTime").text("creation time: " + article["creationTime"]);

                    $articles.find("div > ul").append($tpl);
                });
            });
        })
    </script>
</@c.page>
