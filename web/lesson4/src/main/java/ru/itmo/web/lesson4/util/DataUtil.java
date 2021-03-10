package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {

    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", User.Color.BLACK, "Mike Mirzayanov"),
            new User(6, "pashka", User.Color.RED, "Pavel Mavrin"),
            new User(9, "geranazarov555", User.Color.BLACK, "Georgiy Nazarov"),
            new User(11, "tourist", User.Color.BLUE ,"Gennady Korotkevich")
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(80540, "Testing Beta Round (Unrated)", "Hello. \n" +
                    "\n" +
                    "Last days I did many improvements in EDU. I afraid it can affect the contest interface. So I invite you to take part in contest, just to test the system. Please, try to hacks: there were some changes in them. Please, don't expect new or interesting problems. It is just a test. Unrated.\n" +
                    "\n" +
                    "The problems contain extremely weak pretests. Time limits are too tight. I made my best to increase number of hacks :-)\n" +
                    "\n" +
                    "Thanks, Mike.", 1),
            new Post(82217, "ITMO Algorithms Course", "Hello Codeforces!\n" +
                    "\n" +
                    "I teach a course on algorithms and data structures at ITMO University. During the last year I was streaming all my lectures on Twitch and uploaded the videos on Youtube.\n" +
                    "\n" +
                    "This year I want to try to do it in English.\n" +
                    "\n" +
                    "This is a four-semester course. The rough plan for the first semester:\n" +
                    "\n" +
                    "Algorithms, complexity, asymptotics\n" +
                    "Sorting algorithms\n" +
                    "Binary heap\n" +
                    "Binary search\n" +
                    "Linked lists, Stack, Queue\n" +
                    "Amortized analysis\n" +
                    "Fibonacci Heap\n" +
                    "Disjoint Set Union\n" +
                    "Dynamic Programming\n" +
                    "Hash Tables\n" +
                    "The lectures are open for everybody. If you want to attend, please fill out this form to help me pick the optimal day and time.\n" +
                    "\n" +
                    "See you!", 6),
            new Post(67773, "Codeforces Round #568 (Div. 2)", "Hello, Codeforces!\n" +
                    "\n" +
                    "We are glad to invite you to take part in Codeforces Round #568 (Div. 2), which will be held on среда, 19 июня 2019 г. в 17:45. The round will consist of 7 problems (possibly, plus subproblems). It will be rated for Div. 2 participants.\n" +
                    "\n" +
                    "We, geranazavr555 and cannor147, are students of ITMO University. And we have recently joined the developers team of Codeforces and Polygon. We have prepared this round for more careful acquaintance with the system. We hope that you will enjoy the competition.\n" +
                    "\n" +
                    "Initially, we had prepared the round for Div. 3, but after testing, it became clear that this round is harder than usual such rounds. MikeMirzayanov suggested to make this to be rated for the second division.\n" +
                    "\n" +
                    "Many thanks to MikeMirzayanov for the tremendous work on the creation and development of Codeforces and Polygon and coordinating our work. Also, special thanks to Vshining, PikMike, ZeroAmbition, Vovuh, Lewin, Dave11ar, T-D-K and Azat_Yusupov for testing the round.\n" +
                    "\n" +
                    "Good luck!", 9)

    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
