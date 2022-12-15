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
    if (this.$route.query.tid === undefined) {
      this.ruleForm.tid = 6
    } else {
      this.ruleForm.tid = this.$route.query.tid
    }
    axios.get(this.api.global + 'teacher/findById/' + this.ruleForm.tid)
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

        })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          if (that.ruleForm.tname === 'admin') {
            that.$message({
              showClose: true,
              message: 'admin 不可编辑',
              type: 'error'
            });
            this.$router.push('/queryTeacher')
            return
          }
          console.log(this.ruleForm)
          this.ruleForm.teacherId = this.ruleForm.tid;
          axios.post(that.api.global + "teacher/updateTeacher", this.ruleForm).then(function (resp) {
            if ('000000' === resp.data.returnCode) {
              that.$message({
                showClose: true,
                message: '编辑' + resp.data.returnMsg,
                type: 'success'
              });
            } else {
              that.$message.error(resp.data.returnMsg);
            }
            that.$router.push("/queryTeacher")
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