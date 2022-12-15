<template>
  <el-container>
    <el-main>
      <el-card>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="姓名" prop="name">
            <el-input v-model.name="ruleForm.name" :value="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="pass">
            <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-main>
  </el-container>
</template>
<script>
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        password: '',
        checkPass: '',
        name: sessionStorage.getItem('name')
      },
      rules: {
        password: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
        name: [
          {require: true, message: '名字不能为空', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const that = this
          sessionStorage.setItem('name', that.ruleForm.name)
          const type = sessionStorage.getItem('type')
          let form = null
          let ss = null
          if (type === 'student') {
            ss = 'Student'
            form = {
              studentId: sessionStorage.getItem('sid'),
              studentName: that.ruleForm.name,
              studentPwd: that.ruleForm.password,
            }
          } else {
            ss = 'Teacher'
            form = {
              teacherId: sessionStorage.getItem('tid'),
              teacherName: that.ruleForm.name,
              teacherPwd: that.ruleForm.password,
            }
          }

          //修改密码
          console.log("ddd===" + this.api.globalUrl + type + '/update' + ss)
          axios.post(this.api.globalUrl + type + '/update' + ss, form)
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
                that.$router.push("/" + type + 'Home')
              })
        } else {
          console.log('error submit!!');
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