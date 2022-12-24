<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="课程名" prop="courseName">
        <el-input v-model="ruleForm.courseName"></el-input>
      </el-form-item>
      <el-form-item label="学分" prop="courseCredit">
        <el-input v-model.number="ruleForm.courseCredit"></el-input>
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
        cname: null,
        ccredit: null
      },
      rules: {
        cname: [
          {required: true, message: '请输入名称', trigger: 'blur'},
        ],
        ccredit: [
          {required: true, message: '请输入学分', trigger: 'change'},
          {type: 'number', message: '请输入数字', trigger: 'blur'},
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
          // console.log(this.ruleForm)

          axios.post(that.api.globalUrl + "course/save", this.ruleForm).then(function (resp) {
            console.log(resp)
            if ('000000' === resp.data.returnCode) {
              that.$message({
                showClose: true,
                message: '插入' + resp.data.returnMsg,
                type: 'success'
              });
            } else {
              that.$message.error(resp.data.returnMsg);
            }
            that.$router.push("/courseList")
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