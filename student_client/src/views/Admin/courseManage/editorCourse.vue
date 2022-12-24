<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="课程名称" prop="courseName">
        <el-input v-model="ruleForm.courseName" :value="ruleForm.courseName"></el-input>
      </el-form-item>
      <el-form-item label="学分" prop="courseCredit">
        <el-input v-model.number="ruleForm.courseCredit" :value="ruleForm.courseCredit"></el-input>
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
        courseId: null,
        courseName: null,
        courseCredit: null
      },
      rules: {
        courseName: [
          {required: true, message: '请输入名称', trigger: 'blur'},
        ],
        courseCredit: [
          {required: true, message: '请输入学分', trigger: 'change'},
          {type: 'number', message: '请输入数字', trigger: 'change'},
        ],
      }
    };
  },
  created() {
    this.findById();
  },
  methods: {
    findById() {
      const that = this
      if (this.$route.query.courseId === undefined) {
        this.ruleForm.courseId = 1
      } else {
        this.ruleForm.courseId = this.$route.query.courseId;
      }
      axios.get(this.api.globalUrl + 'course/findById/' + this.ruleForm.courseId)
          .then((resp) => {
            if ('000000' === resp.data.returnCode && resp.data.data !== null) {
              that.ruleForm = resp.data.data[0];
            } else {
              that.$message({
                showClose: true,
                message: resp.data.returnMsg,
                type: 'error'
              });
            }
          });
    },
    //保存
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this;
          console.log(this.ruleForm)
          axios.post(that.api.globalUrl + "course/updateCourse", this.ruleForm)
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