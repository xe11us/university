<template>
  <tr>
    <td>{{ user.id }}</td>
    <td>{{ user.login }}</td>
    <td>{{ user.name }}</td>
    <td v-if="user.admin">Admin</td>
    <td v-else>User</td>
  </tr>
</template>

<script>
export default {
  name: "User",
  props: ["user"]
}
</script>

<style scoped>

</style>