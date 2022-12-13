<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="学号" prop="sid">
              <el-input v-model.number="ruleForm.sid"></el-input>
            </el-form-item>
            <el-form-item label="学生姓名" prop="studentName">
              <el-input v-model="ruleForm.sname"></el-input>
            </el-form-item>
            <el-form-item label="模糊查询" prop="studentPwd">
              <el-switch v-model="ruleForm.password"></el-switch>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">查询</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
              <el-button @click="flush('ruleForm')">刷新</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      ruleForm: {
        sid: null,
        studentName: null,
        studentPwd: true
      },
      rules: {
        sid: [
          { type: 'number', message: '必须是数字类型' }
        ],
        studentName: [

        ],
      }
    };
  },
  create() {
    this.sid = null
    this.studentName = null
    this.studentPwd = true
  },
  methods: {
    flush(formName) {
      this.$router.push('/queryStudent');
      this.$refs[formName].resetFields();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.ruleForm.studentPwd === true) {
            this.ruleForm.studentPwd = 'fuzzy'
          }
          else {
            this.ruleForm.studentPwd = null
          }
          let url = null
          if (this.ruleForm.sid === null && this.ruleForm.studentName === null) {
            url = '/studentList'
          }
          else {
            url = '/queryStudent/studentList'
          }
          this.$router.push({
            path: url,
            query: {
              ruleForm: this.ruleForm
            }})
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