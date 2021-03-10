<template>
  <div>
    <Post v-for="post in viewPosts"
          :post="post"
          :userLogin="mapUserLoginToPostId[post.id]"
          :commentsAmount="mapCommentsAmountToPostId[post.id]"
          :key="post.id"/>
  </div>
</template>
<script>
import Post from "@/components/middle/Post";

export default {
  name: "Index",
  components: {
      Post
  },
  computed: {
    viewPosts: function() {
      return Object.keys(this.mapCommentsAmountToPostId)
          .sort((a, b) => this.posts[a].id - this.posts[b].id)
          .map(p => this.posts[p]);
    }
  },
  props: ["posts", "mapUserLoginToPostId", "mapCommentsAmountToPostId"]
}
</script>

<style scoped>

</style>