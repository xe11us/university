<template>
  <div class="users datatable">
    <table>
      <thead>
      <tr>
        <th>Id</th>
        <th>Login</th>
        <th>Name</th>
        <th>Admin</th>
      </tr>
      </thead>
      <tbody>
        <User v-for="user in users" :user="user" :key="user.id"/>
    </tbody>
    </table>
  </div>
</template>

<script>
import User from "@/components/middle/users/User";

export default {
  name: "Users",
  components: {
    User
  },
  props: ["users"]
}
</script>

<style scoped>

</style>