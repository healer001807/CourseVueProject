<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="学生姓名" prop="studentName">
        <el-input v-model="ruleForm.sname" :value="ruleForm.studentName"></el-input>
      </el-form-item>
      <el-form-item label="初始密码" prop="studentPwd">
        <el-input v-model="ruleForm.password" :value="ruleForm.studentPwd"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button @click="test">test</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      ruleForm: {
        sid: null,
        studentName: null,
        studentPwd: null
      },
      rules: {
        studentName: [
          {required: true, message: '请输入名称', trigger: 'blur'},
          {min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur'}
        ],
        studentPwd: [
          {required: true, message: '请输入密码', trigger: 'change'}
        ],
      }
    };
  },
  created() {
    const that = this
    if (this.$route.query.sid === undefined) {
      this.ruleForm.sid = 1
    } else {
      this.ruleForm.sid = this.$route.query.sid
    }
    axios.get(that.api.globalUrl + 'student/findById/' + this.ruleForm.sid)
        .then((resp) => {
          if ("000000" === resp.data.returnCode) {
            that.ruleForm = resp.data.data;
          } else {
            that.$message({
              showClose: true,
              message: resp.data.returnMsg,
              type: 'error'
            });
          }
        });
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          this.ruleForm.studentId = this.ruleForm.sid;
          axios.post(that.api.globalUrl + 'student/updateStudent', this.ruleForm)
              .then(function (resp) {
                if ('000000' === resp.data.returnCode) {
                  that.$message({
                    showClose: true,
                    message: '编辑' + resp.data.returnMsg,
                    type: 'success'
                  });
                } else {
                  that.$message.error(resp.data.returnMsg);
                }
                that.$router.push("/studentList")
              })
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>