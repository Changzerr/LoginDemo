<template>
  <el-container>
    <h1>{{admin.username}}</h1>
    <el-header style="background-color: #12282a">
      <Header></Header>
    </el-header>

    <el-container>
      <el-aside >
        <Nav></Nav>
      </el-aside>

      <el-main>
        <BreadCrumb style="margin-bottom: 20px"></BreadCrumb>
        <hr>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import Header from "../components/Header";
  import Nav from "../components/Nav";
  import BreadCrumb from "../components/BreadCrumb";
    export default {
        name: "Index",
      components: {Nav, Header, BreadCrumb},
      data(){
          return {
            admin: ''
          }
      },
      created() {
          this.admin = JSON.parse(window.localStorage.getItem("access-admin"))
      }
    }
</script>

<style scoped>

</style>
