<template>
  <div>

    <el-container>
      <el-main>
        <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px"
                 class="demo-ruleForm">
          <el-form-item label="课程号" prop="courseId">
            <el-input v-model.number="ruleForm.courseId"></el-input>
          </el-form-item>
          <el-form-item label="课程名" prop="courseName">
            <el-input v-model="ruleForm.courseName"></el-input>
          </el-form-item>
          <el-form-item label="学分下限" prop="lowBound">
            <el-input v-model.number="ruleForm.lowBound"></el-input>
          </el-form-item>
          <el-form-item label="学分上限" prop="highBound">
            <el-input v-model.number="ruleForm.highBound"></el-input>
          </el-form-item>
          <el-form-item label="模糊查询" prop="fuzzy">
            <el-switch v-model="ruleForm.fuzzy"></el-switch>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">查询</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
            <el-button type="primary" @click="addForm('ruleForm')">新增</el-button>
          </el-form-item>
        </el-form>
        <router-view></router-view>
      </el-main>
    </el-container>

    <el-container>
      <el-main>
        <el-table
            :data="tableData"
            style="width: 100%">
          <el-table-column
              prop="courseId"
              label="课程号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="courseName"
              label="课程名"
              width="180">
          </el-table-column>
          <el-table-column
              prop="courseCredit"
              label="学分"
              width="180">
          </el-table-column>
          <el-table-column
              label="操作"
              width="250">
            <template slot-scope="scope">
              <el-button @click="editor(scope.row)" type="primary" size="small">编辑</el-button>
              <el-button slot="reference" @click="open(scope.row)" type="danger" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination

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
  methods: {
    //删除确认框
    open(row) {
      this.$confirm('此操作将永久删除' + row.courseId + '号课程的信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteCourse(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //删除课程
    deleteCourse(row) {
      const that = this
      axios.get(that.api.globalUrl + 'course/deleteById/' + row.courseId)
          .then((resp) => {
            if ('000000' === resp.data.returnCode) {
              that.$message({
                showClose: true,
                message: '删除' + resp.data.returnMsg,
                type: 'success'
              });
              // window.location.reload()
            } else {
              that.$message({
                showClose: true,
                message: resp.data.returnMsg,
                type: 'error'
              });
            }
          }).catch(function (error) {
        that.$message({
          showClose: true,
          message: '删除出错，存在外键依赖',
          type: 'error'
        });
      }).finally(() =>{
        that.findAll();
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //按条件查询
          this.findBySearch();
        } else {
          return;
        }
      });

    },
    findBySearch() {
      axios.post(this.api.globalUrl + 'course/findBySearch', this.ruleForm)
          .then((resp) => {
            if ('000000' === resp.data.returnCode) {
              this.tableData = resp.data.data;
            } else {
              this.$message({
                showClose: true,
                message: resp.data.returnMsg,
                type: 'error'
              });
            }
          });
    },
    /*offer(row) {
      const teacherId = sessionStorage.getItem("teacherId")
      const courseId = row.courseId
      const term = sessionStorage.getItem("currentTerm")

      const that = this
      axios.get(that.api.globalUrl + 'courseTeacher/insert/' + courseId + '/' + teacherId + '/' + term).then(function (resp) {
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '开设成功',
            type: 'success'
          });
          window.location.reload()
        } else {
          that.$message({
            showClose: true,
            message: '开设失败，请联系管理员',
            type: 'error'
          });
        }
      });
    },*/
    findAll() {
      const that = this;
      //查询全部课程
      axios.get(that.api.globalUrl + 'course/findAll/' + that.pageNum + '/' + that.pageSize)
          .then((resp) => {
            if ('000000' === resp.data.returnCode) {
              that.tableData = resp.data.data.list;
              that.total = resp.data.data.total;
            } else {
              that.$message({
                showClose: true,
                message: resp.data.returnMsg,
                type: 'error'
              });
            }
          });
    },
    changePage(pageNum) {
      const that = this;
      that.pageNum = pageNum
      //调用
      that.findAll();
    },
    editor(row) {
      this.$router.push({
        path: '/editorCourse',
        query: {
          courseId: row.courseId
        }
      });
    },
    addForm(formName) {
      this.$router.push({
        path: '/addCourse',
        query: {}
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
  created() {
    this.findAll();
  },
  data() {
    return {
      tableData: null,
      pageSize: 10,
      pageNum: 1,
      total: null,
      tmpList: null,
      type: sessionStorage.getItem("type"),
      ruleForm: {
        courseId: '',
        courseName: '',
        courseCredit: '',
        highBound: '',
        lowBound: '',
        fuzzy: true
      }
    }
  },
  // props: {
  //   ruleForm: Object,
  //   isActive: Boolean
  // },
  watch: {
    ruleForm: {
      // handler(newRuleForm, oldRuleForm) {
      //   console.log("组件监听 form")
      //   console.log(newRuleForm)
      //   const that = this
      //   that.tmpList = null
      //   that.total = null
      //   that.tableData = null
      //   /*axios.post(that.api.globalUrl + "course/findBySearch", newRuleForm)
      //       .then((resp) => {
      //         console.log("查询结果:");
      //         console.log(resp)
      //         // that.tmpList = resp.data
      //         // that.total = resp.data.length
      //         // let start = 0, end = that.pageSize
      //         // let length = that.tmpList.length
      //         // let ans = (end < length) ? end : length
      //         // that.tableData = that.tmpList.slice(start, ans)
      //       });*/
      // },
      deep: true,
      immediate: true
    }
  },
}
</script>