<template>
  <div>

    <el-container>
      <el-main>
        <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px"
                 class="demo-ruleForm">
          <el-form-item label="学号" prop="sid">
            <el-input v-model.number="ruleForm.studentId"></el-input>
          </el-form-item>
          <el-form-item label="学生名" prop="sname">
            <el-input v-model="ruleForm.studentName"></el-input>
          </el-form-item>
          <el-form-item label="模糊查询" prop="sFuzzy">
            <el-switch v-model="ruleForm.sFuzzy" inactive-value="false"></el-switch>
          </el-form-item>
          <el-form-item label="工号" prop="tid">
            <el-input v-model.number="ruleForm.teacherId"></el-input>
          </el-form-item>
          <el-form-item label="教师名" prop="tname">
            <el-input v-model="ruleForm.teacherName"></el-input>
          </el-form-item>
          <el-form-item label="模糊查询" prop="tFuzzy">
            <el-switch v-model="ruleForm.tFuzzy" inactive-value="false"></el-switch>
          </el-form-item>
          <el-form-item label="课程号" prop="cid">
            <el-input v-model.number="ruleForm.courseId"></el-input>
          </el-form-item>
          <el-form-item label="课程名" prop="cname">
            <el-input v-model="ruleForm.courseName"></el-input>
          </el-form-item>
          <el-form-item label="模糊查询" prop="cFuzzy">
            <el-switch v-model="ruleForm.cFuzzy" inactive-value="false"></el-switch>
          </el-form-item>
          <el-form-item label="成绩下限" prop="lowBound">
            <el-input v-model.number="ruleForm.lowBound"></el-input>
          </el-form-item>
          <el-form-item label="成绩上限" prop="highBound">
            <el-input v-model.number="ruleForm.highBound"></el-input>
          </el-form-item>
          <el-form-item label="选择学期">
            <el-select v-model="ruleForm.term" placeholder="请选择学期">
              <el-option v-for="(item, index) in termList" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">查询</el-button>
            <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>

    <el-container>
      <el-main>
        <el-table
            :data="tableData"
            border
            stripe
            style="width: 100%">
          <el-table-column
              fixed
              prop="courseId"
              label="课程号"
              width="150">
          </el-table-column>
          <el-table-column
              prop="courseName"
              label="课程名"
              width="150">
          </el-table-column>
          <el-table-column
              fixed
              prop="teacherId"
              label="工号"
              width="100">
          </el-table-column>
          <el-table-column
              prop="teacherName"
              label="教师名"
              width="100">
          </el-table-column>
          <el-table-column
              fixed
              prop="studentId"
              label="学号"
              width="100">
          </el-table-column>
          <el-table-column
              prop="studentName"
              label="学生名"
              width="100">
          </el-table-column>
          <el-table-column
              prop="grade"
              label="成绩"
              width="100">
          </el-table-column>
          <el-table-column
              prop="term"
              label="学期"
              width="100">
          </el-table-column>
          <el-table-column
              label="操作"
              width="200">
            <template slot-scope="scope">
              <el-button @click="editor(scope.row)" type="primary" size="small">编辑</el-button>
              <el-button slot="reference" type="danger" size="small">删除</el-button>
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
      pageSize: 10,
      pageNum: 1,
      total: null,
      tmpList: null,
      termList: {},
      ruleForm: {
        studentId: null,
        studentName: null,
        sFuzzy: true,
        teacherId: null,
        teacherName: null,
        tFuzzy: true,
        courseName: null,
        courseId: null,
        cFuzzy: true,
        lowBound: null,
        highBound: null,
        term: sessionStorage.getItem('currentTerm'),
        courseTeacherId: null
      },
      rules: {
        cid: [
          {type: 'number', message: '必须是数字类型'}
        ],
        tid: [
          {type: 'number', message: '必须是数字类型'}
        ],
        sid: [
          {type: 'number', message: '必须是数字类型'}
        ],
        cname: [],
        lowBound: [
          {type: 'number', message: '必须是数字类型'}
        ],
        highBound: [
          {type: 'number', message: '必须是数字类型'}
        ],
      }
    }
  },
  methods: {
    select(row) {
      console.log(row)
    },
    // 删除学生成绩
    deleteTeacher(row) {
      const that = this
      console.log(row)
      const sid = row.sid
      const cid = row.cid
      const tid = row.tid
      const term = row.term
      axios.get("http://localhost:10086/SCT/deleteById/" + sid + '/' + cid + '/' + tid + '/' + term).then(function (resp) {
        console.log(resp)
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '删除成功',
            type: 'success'
          });
          window.location.reload()
        } else {
          that.$message({
            showClose: true,
            message: '删除出错，请查询数据库连接',
            type: 'error'
          });
        }
      }).catch(function (error) {
        that.$message({
          showClose: true,
          message: '删除出错，存在外键依赖',
          type: 'error'
        });
      })
    },
    // 切换页码
    changePage(pageNum) {
      const that = this;
      that.pageNum = pageNum;
      that.findAll();
    },
    editor(row) {
      this.$router.push({
        path: '/editorGradeCourse',
        query: {
          // courseId: row.courseId,
          // teacherId: row.teacherId,
          // studentId: row.studentId,
          // term: row.term
          editInfo:row
        }
      })
    },
    //查询所有
    findAll() {
      debugger;
      const that = this;
      that.findAllTerm();
      const params = {
        'pageNum': that.pageNum,
        'pageSize': that.pageSize,
        'term': that.ruleForm.term
      }
      axios.post(that.api.globalUrl + 'SCT/findAll', params)
          .then(resp => {
            if ('000000' === resp.data.returnCode) {
              that.tableData = resp.data.data.list;
              that.total = resp.data.data.total;
            } else {
              that.$message.error(resp.data.returnMsg);
            }
          })
          .catch((e) => {
            that.$message({
              showClose: true,
              message: '查询失败',
              type: 'error'
            });
          });
    },
    findAllTerm() {
      const that = this;
      axios.get(that.api.globalUrl + 'SCT/findAllTerm')
          .then(resp => {
            if ('000000' === resp.data.returnCode) {
              console.log("查询学期"+resp.data.data);
              that.termList = resp.data.data;
            } else {
              that.$message.error(resp.data.returnMsg);
            }
          })
          .catch((e) => {
            that.$message({
              showClose: true,
              message: '查询失败',
              type: 'error'
            });
          });
    },
    // 提交表单（查询）
    submitForm(formName) {
      const that = this;
      that.$refs[formName].validate((valid) => {
        if (valid) {
          that.findBySearch();
        }
      });
    },
    // 重置表单
    resetForm(formName){
      this.$refs[formName].resetFields();
    }
  },

  // props: {
  //   ruleForm: Object,
  // },
  created() {
    debugger;
    this.findAll();
  },
}
</script>