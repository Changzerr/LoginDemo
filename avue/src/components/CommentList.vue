<template>
  <div>
    <el-table
    :data="tableData"
    stripe
    style="width: 100%">
    <el-table-column v-for="(tabName ,prop) in tabBar" :label="tabName" :prop="prop" :key="id" :show-overflow-tooltip='true'>
      <slot :name="prop"></slot>
    </el-table-column>


    <el-table-column
      prop="enable"
      label="通过"
      width="70"
      :formatter="commentFormatter">
    </el-table-column>

    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button type="danger" icon="el-icon-delete" circle @click="deleteComment(scope.row)"></el-button>
        <el-button v-if="scope.row.enable === 0" type="success" icon="el-icon-check" circle @click="AcComment(scope.row)"></el-button>

      </template>

    </el-table-column>
  </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[10,20,40]"
      :page-size="pageSize"
      :total="totalNum"
      layout="total, sizes, prev, pager, next, jumper"
    >
    </el-pagination>

  </div>
</template>


<script>
  import Pagination from "./Pagination";
  export default {
    name: "CommentList",
    components: {Pagination},
    data() {
      return {
        tabBar:{
          id:"id",
          name: "评论人",
          email: "评论邮箱",
          title: "博客标题",
          content: "评论内容"
        },
        tableData: [],
        totalNum: 0,
        pageNum: 1,
        pageSize: 10
      }
    },
    methods: {
      getList() {
        this.$ajax.get('http://localhost:8088/commentPage', {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        }).then(res => {
          this.totalNum = res.data.obj.total;
          this.tableData = res.data.obj.list;
          i
          // this.totalNum = this.tableData.length
        }).catch(function (error) {
          console.log(error);
        });
      },
      handleSizeChange(pageSize){
        this.pageSize = pageSize
        this.getList()
      },
      handleCurrentChange(pageNum){
        pageNum: this.pageNum = pageNum
        this.getList()
      },
      deleteComment(val) {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const list = this.tableData.filter(item => item !== val);
          this.tableData = list;
          const id = val.id;
          this.$ajax.delete('http://localhost:8088/comment/'+id).then(res => {
            // this.tableData = res.data;
            // this.tableData=res;
            // console.log(res.data)
            console.log(res)
          }).catch(function (error) {
            console.log(error);
          });
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },

      AcComment(val) {
        val.enable = 1
        this.$ajax.post('http://localhost:8088/comment/' + val.id).then(res => {
          console.log(res)

        }).catch(function (error) {
          console.log(error);
        });
        this.$message({
          message: '通过评论成功',
          type: 'success'
        });
      },

      commentFormatter(row){
        // console.log(row)
        if (row.enable){
          return "是"
        }else{
          return "否"
        }
      }
    },
    created() {

      this.getList()

    }
  }
</script>
<style scoped>

</style>
