<template>
  <div>
    <el-table
      :data="tableData"
      stripe
      style="width: 100%">

      <el-table-column
        :index="indexMethod"
        type="index"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="title"
        label="博客标题"
        width="180"
        :show-overflow-tooltip='true'>
      </el-table-column>
      <el-table-column
        prop="description"
        label="博客摘要"
        :show-overflow-tooltip='true'>
      </el-table-column>
      <el-table-column
        prop="created"
        label="发布日期"
        width="100"
        :show-overflow-tooltip='true'
        :formatter="dateFormatter">
      </el-table-column>
      <el-table-column
        prop="category"
        label="所属类别"
        width="80"
        :show-overflow-tooltip='true'>
      </el-table-column>

      <el-table-column
        prop="tags"
        label="标签"
        width="110"
        :show-overflow-tooltip='true'>
      </el-table-column>\
      <el-table-column
        prop="licks"
        label="喜欢"
        width="80">
      </el-table-column>
      <el-table-column
        prop="enablecomment"
        label="可评论"
        width="70"
        :formatter="commentFormatter">

      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="danger" icon="el-icon-delete" circle @click="deleteBlog(scope.row)"></el-button>

          <el-button type="primary" icon="el-icon-edit" circle circle @click="updateBlog(scope.row)"></el-button>

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
    export default {
        name: "blogList",
      data() {
        return {
          tableData: [],
          totalNum: 0,
          pageNum: 1,
          pageSize: 10
        }
      },
      methods: {
        getList() {
          this.$ajax.get('http://localhost:8088/blogPage', {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize
            }
          }).then(res => {
            this.totalNum = res.data.obj.total;
            this.tableData = res.data.obj.list;
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
        indexMethod(index) {
          return (this.pageNum - 1) * this.pageSize + index + 1
        },
        deleteBlog(val) {
          this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            const list = this.tableData.filter(item => item !== val);
            this.tableData = list;
            const id = val.id;
            this.$ajax.delete('http://localhost:8088/blog/'+id).then(res => {
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
        updateBlog(val){
          var arr=JSON.stringify(val)
          // this.$router.push("/details?obj="+encodeURIComponent(arr))
          this.$router.push({name:"updateBlog",params:{...val}})
        },
        dateFormatter (row, column) {
          let datetime = row[column.property];
          if(datetime){
            datetime = new Date(datetime);
            let y = datetime.getFullYear() + '-';
            let mon = datetime.getMonth()+1 + '-';
            let d = datetime.getDate();
            return y + mon + d;
          }
          return ''
        },
        commentFormatter(row){
          // console.log(row)
          if (row.enablecomment){
            return "是"
          }else{
            return "否"
          }
        },

      },

      created() {
        this.getList()
      }
    }
</script>

<style scoped>

</style>
