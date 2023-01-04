<template>
  <div>
    <el-card>
      <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
               class="demo-ruleForm">
        <el-form-item label="课程名称" prop="cname">
          <el-input v-model="ruleForm.courseName" :value="ruleForm.courseName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="教师名" prop="tname">
          <el-input v-model="ruleForm.teacherName" :value="ruleForm.teacherName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="学生名" prop="sname">
          <el-input v-model="ruleForm.studentName" :value="ruleForm.studentName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="分数" prop="grade">
          <el-input v-model.number="ruleForm.grade" :value="ruleForm.grade"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    const checkGrade = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('成绩不能为空'));
      }
      if (!Number.isInteger(value)) {
        callback(new Error('请输入数字值'));
      } else {
        if (value > 100 || value < 0) {
          callback(new Error('成绩范围 [0 - 100]'));
        } else {
          callback();
        }
      }
    };
    return {
      ruleForm: {},
      rules: {
        grade: [
          {required: true, message: '请输入学分', trigger: 'change'},
          {type: 'number', message: '请输入数字', trigger: 'change'},
          {validator: checkGrade, trigger: 'blur'}
        ],
      }
    };
  },
  created() {
    this.findGradeById();
  },
  methods: {
    // 根据id查询成绩
    findGradeById() {
      console.log("修改成绩" + JSON.stringify(this.$route.query.editInfo));
      if (undefined !== this.$route.query.editInfo && null !== this.$route.query.editInfo) {
        this.ruleForm = this.$route.query.editInfo;
      }
    },
    // 修改成绩
    editGradeById() {
      const that = this

      const params = {
        'scoreId': that.ruleForm.scoreId,
        'grade': that.ruleForm.grade,
        'term': that.ruleForm.term
      }
      axios.post(that.api.globalUrl + "SCT/updateById", params)
          .then(function (resp) {
            if (resp.data.returnCode === '000000') {
              that.$message({
                showClose: true,
                message: '编辑' + resp.data.returnMsg,
                type: 'success'
              });
            } else {
              that.$message.error(resp.data.returnMsg);
            }
            if (sessionStorage.getItem('type') === 'admin') {
              that.$router.push("gradeCourseList")
            } else {
              that.$router.push("/teacherQueryGradeCourseManage")
            }
          });
    },
    // 查询提交
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          this.editGradeById();
        } else {
          return false;
        }
      });
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>