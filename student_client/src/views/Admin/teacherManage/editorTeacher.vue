<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="教师姓名" prop="teacherName">
        <el-input v-model="ruleForm.teacherName" :value="ruleForm.teacherName"></el-input>
      </el-form-item>
      <el-form-item label="初始密码" prop="teacherPwd">
        <el-input v-model="ruleForm.teacherPwd" :value="ruleForm.teacherPwd"></el-input>
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
        teacherId: null,
        teacherName: null,
        teacherPwd: null
      },
      rules: {
        teacherName: [
          {required: true, message: '请输入名称', trigger: 'blur'},
          {min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur'}
        ],
        teacherPwd: [
          {required: true, message: '请输入密码', trigger: 'change'}
        ],
      }
    };
  },
  created() {
    const that = this
    if (that.$route.query.teacherId === undefined) {
      that.ruleForm.teacherId = 6; //不太合理，要是6被删除了，永远为空
    } else {
      that.ruleForm.teacherId = that.$route.query.teacherId
    }
    axios.get(that.api.globalUrl + 'teacher/findById/' + that.ruleForm.teacherId)
        .then((resp) => {
          if ('000000' === resp.data.returnCode) {
            that.ruleForm = resp.data.data
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
    //修改
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          if (that.ruleForm.teacherName === 'admin') {
            that.$message({
              showClose: true,
              message: 'admin 不可编辑',
              type: 'error'
            });
            this.$router.push('/teacherList');
            return;
          }
          axios.post(that.api.globalUrl + "teacher/updateTeacher", this.ruleForm)
              .then((resp) => {
                if ('000000' === resp.data.returnCode) {
                  that.$message({
                    showClose: true,
                    message: '编辑' + resp.data.returnMsg,
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
    //重置
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>