<template>
  <el-header >
    <router-link to="/main"  class="title">manage</router-link>
    <el-dropdown style="text-align: right">
      <i class="el-icon-user-solid" style="margin-right:15px;color: #4594ff;font-size: 20px"></i>
      <el-dropdown-menu slot="dropdown">
        <router-link to="/user/profile" class="sidebar">
          <el-dropdown-item>个人信息</el-dropdown-item>
        </router-link>
        <el-dropdown-item>退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-header>

</template>

<script>
    export default {
        name: "Header"
    }
</script>

<style scoped>
  .title{
    color: #fff;
    width: 260px;
    text-align: left;
    height: 60px;
    font-size: 30px;
    font-weight: 700;
    text-transform: uppercase;
    line-height: 60px;
    text-decoration:none;
    float:left;
  }
  .el-header {
    background-color: #12282a;
    color: #4594ff;
    line-height: 60px;
    text-align: right;
    font-size: 12px;
  }

  .sidebar{
    text-decoration:none;
  }
</style>
