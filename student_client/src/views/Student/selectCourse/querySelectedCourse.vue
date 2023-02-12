<template>
  <div>
    <el-card>
      <el-table
          :data="tableData"
          border
          style="width: 100%">
        <el-table-column
            fixed
            prop="courseId"
            label="课号"
            width="150">
        </el-table-column>
        <el-table-column
            prop="courseName"
            label="课程号"
            width="150">
        </el-table-column>
        <el-table-column
            prop="teacherId"
            label="教师号"
            width="150">
        </el-table-column>
        <el-table-column
            prop="teacherName"
            label="教师名称"
            width="150">
        </el-table-column>
        <el-table-column
            prop="courseCredit"
            label="学分"
            width="150">
        </el-table-column>
        <el-table-column
            label="操作"
            width="100">

          <template slot-scope="scope">
            <el-popconfirm
                confirm-button-text='退课'
                cancel-button-text='取消'
                icon="el-icon-info"
                title="确定退课？"
                @confirm="deleteSCT(scope.row)"
            >
              <el-button slot="reference" type="text" size="small">退课</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pageSize"
          @current-change="changePage"
      >
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  methods: {
    deleteSCT(row) {
      const cid = row.cid
      const tid = row.tid
      const sid = sessionStorage.getItem('sid')
      const term = sessionStorage.getItem('currentTerm')
      const sct = {
        cid: cid,
        tid: tid,
        sid: sid,
        term: term
      }

      const that = this
      axios.post('http://localhost:10086/SCT/deleteBySCT', sct).then(function (resp) {
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '退课成功',
            type: 'success'
          });
          window.location.reload()
        } else {
          that.$message({
            showClose: true,
            message: '退课失败，请联系管理员',
            type: 'error'
          });
        }
      })

    },
    changePage(page) {
      page = page - 1
      const that = this
      let start = page * that.pageSize, end = that.pageSize * (page + 1)
      let length = that.tmpList.length
      let ans = (end < length) ? end : length
      that.tableData = that.tmpList.slice(start, ans)
    },
  },
  data() {
    return {
      tableData: null,
      pageSize: 10,
      total: null,
      tmpList: null,
      type: sessionStorage.getItem('type')
    }
  },
  created() {
    const studentId = sessionStorage.getItem('studentId');
    const term = sessionStorage.getItem('currentTerm');
    const that = this;
    axios.get(that.api.globalUrl + 'SCT//findCourseByStudentId/' + studentId + '/' + term).then(function (resp) {
      if ("000000" === resp.data.returnCode) {
        that.tableData = resp.data.data;
      } else {
        that.$message.error(resp.data.returnMsg);
      }
      // that.total = resp.data.length
      // let start = 0, end = that.pageSize
      // let length = that.tmpList.length
      // let ans = (end < length) ? end : length
      // that.tableData = that.tmpList.slice(start, end)
    })
  },
}
</script>