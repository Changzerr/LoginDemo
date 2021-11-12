<template>
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
</template>

<script>
    export default {
      name: "Pagination",

      data() {
        return {
          tableData: [],
          totalNum: 0,
          pageNum: 1,
          pageSize: 10
        }
      },
      methods: {
        handleSizeChange(pageSize){

        },
        handleCurrentChange(pageNum){

        },
        created() {

        }
      }
    }
</script>

<style scoped>

</style>
