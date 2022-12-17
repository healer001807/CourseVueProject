<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="教师姓名" prop="teacherName">
        <el-input v-model="ruleForm.teacherName"></el-input>
      </el-form-item>
      <el-form-item label="初始密码" prop="teacherPwd">
        <el-input v-model="ruleForm.teacherPwd" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      ruleForm: {
        teacherName: '',
        teacherPwd: ''
      },
      rules: {
        teacherName: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
          {min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur'}
        ],
        teacherPwd: [
          {required: true, message: '请输入密码', trigger: 'change'}
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          if (that.ruleForm.teacherName === 'admin') {
            that.$message({
              showClose: true,
              message: 'admin 不可添加',
              type: 'error'
            });
            return;
          }
          //添加教师
          axios.post(this.api.globalUrl + "teacher/addTeacher", this.ruleForm)
              .then((resp) => {
                if ('000000' === resp.data.returnCode) {
                  that.$message({
                    showClose: true,
                    message: '插入' + resp.data.returnMsg,
                    type: 'success'
                  });
                } else {
                  that.$message.error(resp.data.returnMsg);
                }
                that.$router.push("/teacherList")
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