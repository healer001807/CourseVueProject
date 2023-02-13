<template>
  <div>

    <el-container>
      <el-main>
        <!--        <el-card>-->
        <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="80px"
                 class="demo-ruleForm">
          <el-form-item label="工号" prop="tid">
            <el-input v-model.number="ruleForm.teacherId"></el-input>
          </el-form-item>
          <el-form-item label="教师名" prop="tname">
            <el-input v-model="ruleForm.teacherName"></el-input>
          </el-form-item>
          <el-form-item label="模糊查询">
            <el-switch v-model="ruleForm.tFuzzy" inactive-value="false"></el-switch>
          </el-form-item>
          <el-form-item label="课程号" prop="cid">
            <el-input v-model.number="ruleForm.courseId"></el-input>
          </el-form-item>
          <el-form-item label="课程名" prop="cname">
            <el-input v-model="ruleForm.courseName"></el-input>
          </el-form-item>
          <el-form-item label="模糊查询">
            <el-switch v-model="ruleForm.cFuzzy" inactive-value="false"></el-switch>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">查询</el-button>
            <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
        <!--        </el-card>-->
      </el-main>
    </el-container>

    <el-container>
      <el-main>
        <el-table
            :data="tableData"
            style="width: 100%">
          <el-table-column
              prop="courseId"
              label="课号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="courseName"
              label="课程号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="teacherId"
              label="教师号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="teacherName"
              label="教师名称"
              width="180">
          </el-table-column>
          <el-table-column
              label="操作"
              width="200">
            <template slot-scope="scope">
              <el-button type="primary" size="small" @click="edit(scope.row)">修改</el-button>
              <el-button type="danger" size="small" @click="open(scope.row)">删除</el-button>
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
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {

  data() {
    return {
      tableData: null,
      pageSize: 2,
      pageNum: 1,
      total: null,
      tmpList: null,
      type: sessionStorage.getItem('type'),
      ruleForm: {
        pageNum: 1,
        pageSize: 2,
        teacherId: '',
        courseId: '',
        courseName: '',
        courseCredit: '',
        cFuzzy: true,
        tFuzzy: true
      }
    }
  },
  methods: {
    select(row) {
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
      axios.post('http://localhost:10086/SCT/save', sct).then(function (resp) {
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '选课成功',
            type: 'success'
          });
        } else {
          that.$message({
            showClose: true,
            message: '选课出错，请联系管理员',
            type: 'error'
          });
        }
      })

    },
    open(row) {
      this.$confirm('此操作将永久删除课程编号为' + row.courseId + '的课程, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        // 调用删除
        this.deleteCourseTeacher(row);
        // this.$message({
        //   type: 'info',
        //   message: '已取消'
        // });
      }).catch(action => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 删除教师课设课程
    deleteCourseTeacher(row) {
      debugger;
      const that = this
      axios.post(that.api.globalUrl + 'courseTeacher/deleteById', row).then(function (resp) {
        if (resp.data.returnCode === '000000') {
          that.$message({
            showClose: true,
            message: '删除' + resp.data.returnMsg,
            type: 'success'
          });
          //window.location.reload()
        } else {
          that.$message({
            showClose: true,
            message: resp.data.returnMsg,
            type: 'error'
          });
        }
      }).finally(() => {
        that.findAll();
      });
    },
    // 切换页码
    changePage(page) {
      const that = this;
      that.pageNum = page;
      that.findCourseTeacherInfo();
    },

    // 查询教师开课
    findCourseTeacherInfo() {
      debugger;
      const that = this;
      that.ruleForm.pageNum = that.pageNum;
      that.ruleForm.pageSize = that.pageSize;
      const params = that.ruleForm;
      axios.post(that.api.globalUrl + "courseTeacher/findCourseTeacherInfo", params)
          .then((resp) => {
            if ('000000' === resp.data.returnCode) {
              debugger;
              that.total = resp.data.data.total;
              that.tableData = resp.data.data.list;
            } else {
              that.$message.error(resp.data.returnMsg);
            }
          }).catch((e) => {
        that.$message.error("系统异常");
      });
    },
    //查询开课
    findAll() {
      this.findCourseTeacherInfo();

    },
    // 表单查询
    submitForm(formName) {
      const that = this;
      that.$refs[formName].validate((valid) => {
        if (valid) {
          debugger;
          this.findCourseTeacherInfo();
        }
      })
    },
    // 重置表单
    resetForm(formName) {
      const that =this;
      that.$refs[formName].resetFields();
    },
  },
  created() {
    this.findAll();
  },


  watch: {
    // ruleForm: {
    //   handler(newRuleForm, oldRuleForm) {
    //     const that = this
    //     that.tmpList = null
    //     that.total = null
    //     that.tableData = null
    //     // axios.post("http://localhost:10086/courseTeacher/findCourseTeacherInfo", newRuleForm).then(function (resp) {
    //     //   that.tmpList = resp.data
    //     //   that.total = resp.data.length
    //     //   let start = 0, end = that.pageSize
    //     //   let length = that.tmpList.length
    //     //   let ans = (end < length) ? end : length
    //     //   that.tableData = that.tmpList.slice(start, ans)
    //     // })
    //   },
    //   deep: true,
    //   immediate: true
    // }
  },
}
</script>