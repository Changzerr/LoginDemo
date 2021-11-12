<template>
  <div class="m-container">
    <div class="m-content">
      <el-form ref="editForm" status-icon :model="editForm" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="editForm.title"></el-input>
        </el-form-item>

        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="editForm.description"></el-input>
        </el-form-item>
        <el-form-item label="所属类别" prop="description">
          <el-select v-model="editForm.category" placeholder="请选择">
            <el-option
              v-for="item in categoryOptions"
              :key="item.id"
              :label="item.category"
              :value="item.category">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="tag" prop="description">
          <el-select v-model="editForm.tags" placeholder="请选择">
            <el-option
              v-for="item in tagOptions"
              :key="item.id"
              :label="item.tags"
              :value="item.tags">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开启评论" prop="description">
          <el-switch
            v-model="value">
          </el-switch>
        </el-form-item>



        <el-form-item label="内容" prop="content">
          <mavon-editor
            v-model="editForm.content"
            style="height: 150px"></mavon-editor>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm()">上传</el-button>
          <router-link to="/content/blogList">
            <el-button>取消</el-button>
          </router-link>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
    export default {
        name: "BlogEdit",
      data() {
        return {
          tagOptions: [],
          categoryOptions: [],
          value: true,
          editForm: {
            title: '',
            description: '',
            content: '',
            tags: [],
            enablecomment: '',
            category: ''
          },
          rules: {
            title: [
              {required: true, message: '请输入标题', trigger: 'blur'},
              {min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur'}
            ],
            description: [
              {required: true, message: '请输入摘要', trigger: 'blur'}
            ]
          }
        }
      },
      created() {
        this.$ajax('http://localhost:8088/tag').then(res => {
          this.tagOptions=res.data.obj;

        }).catch(function (error) {
          console.log(error);
        });

        this.$ajax('http://localhost:8088/category').then(res => {
          this.categoryOptions=res.data.obj;

        }).catch(function (error) {
          console.log(error);
        });
      },
      methods: {
        submitForm() {
          if(this.value){
            this.editForm.enablecomment=1
          }else{
            this.editForm.enablecomment=0
          }
          console.log(this.editForm)

          this.$ajax.post('http://localhost:8088/blog',this.editForm).then(res => {
            console.log(res)

          }).catch(function (error) {
            console.log(error);
          });
          this.$router.push({path: "/content/blogList"})
        }
      }

    }
</script>

<style scoped>

</style>
