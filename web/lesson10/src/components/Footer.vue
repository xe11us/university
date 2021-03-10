<template>
    <footer>
      <div><a href="/">Codeforces</a> &copy; 2020 by Mike Mirzayanov</div>
      <div>Users registered: {{ usersRegistered }}</div>
      <div>Posts written: {{ postsWritten }}</div>
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["usersRegistered", "postsWritten"]
}
</script>

<style scoped>

</style>