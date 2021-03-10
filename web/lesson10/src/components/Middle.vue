<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'"
                   :posts="posts"
                   :mapUserLoginToPostId="mapUserLoginToPostId"
                   :mapCommentsAmountToPostId="mapCommentsAmountToPostId"/>
            <Users v-if="page === 'Users'" :users="viewUsers"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar";
import Index from "@/components/middle/Index";
import Users from "@/components/middle/users/Users";
import Enter from "@/components/middle/Enter";
import Register from "@/components/middle/Register";
import WritePost from "@/components/middle/WritePost";
import EditPost from "@/components/middle/EditPost";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index"
        }
    },
    components: {
        WritePost,
        Enter,
        Register,
        Index,
        Users,
        Sidebar,
        EditPost
    },
    props: ["posts", "users", "comments"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        },
        viewUsers: function () {
          return Object.values(this.users).sort((a, b) => b.id - a.id);
        },
        mapCommentsAmountToPostId: function () {
          let map = {};
          let comments = this.comments;
          Object.values(this.posts).forEach(function (post) {
            map[post.id] = Object.keys(Object.values(comments).filter(c => c.postId === post.id)).length;
          });
          return map;
        },
        mapUserLoginToPostId: function () {
          let map = {};
          let users = this.users;
          Object.values(this.posts).forEach(function (post) {
            map[post.id] = users[post.userId].login;
          })
          return map;
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
    }
}
</script>

<style scoped>

</style>