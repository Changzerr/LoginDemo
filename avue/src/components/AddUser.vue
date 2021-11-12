<template>
  <el-form ref="form" :model="form" label-width="80px">
    <el-form-item label="用户名">
      <el-input v-model="form.username"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="form.password"></el-input>
    </el-form-item>
    <el-form-item label="头像">
      <el-input v-model="form.avatar"></el-input>
    </el-form-item>
    <el-form-item label="Email">
      <el-input v-model="form.email"></el-input>
    </el-form-item>
    <el-form-item label="权限">
      <el-select v-model="form.authority" placeholder="---请选择---">
        <el-option label="admin" value="admin"></el-option>
        <el-option label="user" value="user"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="性别">
      <el-select v-model="form.sex" placeholder="---请选择---">
        <el-option label="男" value=0></el-option>
        <el-option label="女" value=1></el-option>
        <el-option label="保密" value=2></el-option>
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即创建</el-button>
      <router-link to="/user/userList">
        <el-button>取消</el-button>
      </router-link>
    </el-form-item>
  </el-form>

</template>

<script>
    export default {
        name: "AddUser",
      data() {
        return {
          form: {
            username: '',
            password: '',
            avatar: '',
            email: '',
            authority: '',
            sex: ''
          }
        }
      },
      methods: {
        onSubmit() {
          console.log('submit!');
        }
      }
    }
</script>

<style scoped>

</style>
