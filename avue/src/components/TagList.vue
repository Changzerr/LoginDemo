<template>
  <div>

    <el-table
      :data="tableData"
      stripe
      style="width: 100%">
      <el-table-column
        prop="id"
        label="id"
        width="80">
      </el-table-column>
      <el-table-column
        prop="tags"
        label="标签"
        width="180">
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">

          <el-button type="danger" icon="el-icon-delete" circle @click="deleteTag(scope.row)"></el-button>

          <el-button type="primary" icon="el-icon-edit" circle circle @click="updateTag(scope.row)"></el-button>
          <el-button type="warning" icon="el-icon-plus" circle @click="addTag()"></el-button>
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
        name: "ClassifyManageList",
      data() {
        return {
          tableData: [],
          totalNum: 0,
          pageNum: 1,
          pageSize: 10
        }
      },
      methods: {
          getList(){
            this.$ajax.get('http://localhost:8088/tagPage', {
              params: {
                pageNum: this.pageNum,
                pageSize: this.pageSize
              }
            }).then(res => {
              console.log(res.data.obj)
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
          this.pageNum = pageNum
          this.getList()
        },

        deleteTag(val) {
          this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            const list = this.tableData.filter(item => item !== val);
            this.tableData = list;
            const id = val.id;
            this.$ajax.delete('http://localhost:8088/tag/'+id).then(res => {
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
        updateTag(val) {
          this.$prompt('修改标签', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            inputValue: val.tags
          }).then(({ value }) => {
            this.$message({
              type: 'success',
              message: '修改成功 '
            });
            val.tags = value
            this.$ajax.put('http://localhost:8088/tag',val).then(res => {
              console.log(res)

            }).catch(function (error) {
              console.log(error);
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '取消修改'
            });
          });
        },
        addTag(){
          this.$prompt('新建标签', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消'
          }).then(({ value }) => {
            this.$message({
              type: 'success',
              message: '新建成功 '
            });

            this.$ajax.post('http://localhost:8088/tag',{tags: value}).then(res => {
              console.log(res)

            }).catch(function (error) {
              console.log(error);
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '取消新建'
            });
          });
        }
      },
      created() {
        this.getList()
      }
    }
</script>

<style scoped>

</style>
