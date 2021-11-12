<template>
  <div class="">
    <p>profile</p>
    <el-popconfirm title="这是一段内容确定删除吗？">
      <el-button slot="reference">删除</el-button>
    </el-popconfirm>
    <router-link to="/user/userList" class="sidebar">
      <el-button style="background-color:#09c048;color:white">更改</el-button>

    </router-link>
  </div>

</template>

<script>
    export default {
        name: "Profile"
    }
</script>

<style scoped>

</style>
