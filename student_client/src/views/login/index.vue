<template>
  <div>
    <el-container>
      <el-header>
        <div style="text-align: center; font-size: 25px; font-weight: bolder">
          <i class="el-icon-s-home" style="margin-right: 25px"></i>
          选课管理系统
        </div>
      </el-header>
      <el-main>
        <el-card class="login-module">
          <div slot="header" class="clearfix">
            <span style="text-align: center; font-size: 20px; font-family: 'Microsoft YaHei'">
              <p><i class="el-icon-office-building" style="margin-right: 18px"></i>登陆</p>
            </span>
          </div>
          <div>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
              <el-form-item label="用户 id" prop="id">
                <el-input v-model.number="ruleForm.id" prefix-icon="el-icon-lollipop"></el-input>
              </el-form-item>
              <el-form-item label="用户密码" prop="password">
                <el-input v-model="ruleForm.password" placeholder="请输入密码" show-password
                          prefix-icon="el-icon-ice-cream-round"></el-input>
              </el-form-item>
              <el-form-item label="用户类型" prop="type">
                <el-radio-group v-model="ruleForm.type">
                  <el-radio label="student" value="student">学生</el-radio>
                  <el-radio label="teacher" value="teacher">老师</el-radio>
                  <el-radio label="admin" value="admin">admin</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">登陆</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
                <el-button @click="test('ruleForm')">test</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      ruleForm: {
        id: null,
        password: null,
        type: null,
      },
      rules: {
        id: [
          {required: true, message: '请输入用户 id', trigger: 'blur'},
          {type: 'number', message: '请输入数字', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '请选择', trigger: 'change'}
        ],
      },
      responseData: {}
    };
  },
  methods: {
    submitForm(formName) {
      const that = this
      this.$refs[formName].validate((valid) => {
            if (valid) {
              let check = false
              let name = null

              axios.get(this.api.globalUrl + 'info/getCurrentTerm').then(function (resp) {
                sessionStorage.setItem("currentTerm", resp.data)
              })

              axios.get(this.api.globalUrl + 'info/getForbidCourseSelection').then(function (resp) {
                sessionStorage.setItem("ForbidCourseSelection", resp.data)
              })

              if (that.ruleForm.type === 'admin' || that.ruleForm.type === 'teacher') {
                let form = {tid: that.ruleForm.id, password: that.ruleForm.password}
                let params = {
                  teacherId: that.ruleForm.id,
                  teacherPwd: that.ruleForm.password
                }
                axios.post(this.api.globalUrl + "teacher/login", params)
                    .then((resp) => {
                      console.log("教师登陆验证信息：" + resp.data)
                      if ('000000' === resp.data.returnCode) {
                        axios.get(this.api.globalUrl + "teacher/findById/" + that.ruleForm.id)
                            .then(function (resp) {
                              if ('000000' !== resp.data.returnCode) {
                                that.$message({
                                  showClose: true,
                                  message: resp.data.returnMsg,
                                  type: 'error'
                                });
                                return;
                              }
                              console.log("登陆页正在获取用户信息" + resp.data);
                              name = resp.data.data.teacherName

                              sessionStorage.setItem("token", 'true')
                              sessionStorage.setItem("type", that.ruleForm.type)
                              sessionStorage.setItem("name", name)
                              sessionStorage.setItem("tid", resp.data.data.teacherId)

                              if (that.ruleForm.type === 'admin' && name === 'admin') {
                                that.$message({
                                  showClose: true,
                                  message: '登陆成功，欢迎 ' + name + '!',
                                  type: 'success'
                                });
                                that.$router.push('/admin')
                              } else if (that.ruleForm.type === 'teacher' && name !== 'admin') {
                                that.$message({
                                  showClose: true,
                                  message: '登陆成功，欢迎 ' + name + '!',
                                  type: 'success'
                                });
                                that.$router.push('/teacher')
                              } else {
                                that.$message({
                                  showClose: true,
                                  message: 'admin 登陆失败，检查登陆类型',
                                  type: 'error'
                                });
                              }
                            })
                      } else {
                        that.$message({
                          showClose: true,
                          message: resp.data.returnMsg,
                          type: 'error'
                        });
                      }
                    });
              } else if (that.ruleForm.type === 'student') {
                let params = {
                  studentId: that.ruleForm.id,
                  studentPwd: that.ruleForm.password
                }
                //登录
                /*全局url*/
                // axios.post(this.glob.INTERFACES.login, form).then(function (resp) {
                axios.post(this.api.globalUrl + "student/login", params)
                    .then((resp) => {
                      console.log("学生登陆验证信息：" + resp.data);
                      //失败
                      if ("000000" !== resp.data.returnCode) {
                        debugger;
                        that.$message({
                          showClose: true,
                          message: resp.data.returnMsg,
                          type: 'error'
                        });
                        return;
                      }
                      //成功
                      axios.get(this.api.globalUrl + "student/findById/" + that.ruleForm.id).then((resp) => {
                        // 000000-成功
                        if ("000000" !== resp.data.returnCode) {
                          that.$message({
                            showClose: true,
                            message: resp.data.returnMsg,
                            type: 'error'
                          });
                          return;
                        }
                        console.log("登陆页正在获取用户信息" + resp.data)
                        //缓存
                        sessionStorage.setItem("token", 'true');
                        sessionStorage.setItem("type", that.ruleForm.type);
                        sessionStorage.setItem("name", resp.data.data.studentName);
                        sessionStorage.setItem("studentId", resp.data.data.studentId);

                        that.$message({
                          showClose: true,
                          message: '登陆成功，欢迎 ' + resp.data.data.studentName + '!',
                          type: 'success'
                        });

                        console.log('正在跳转：' + '/' + that.ruleForm.type);
                        that.$router.push({
                          path: '/' + that.ruleForm.type,
                          query: {}
                        });
                      });
                    });

              } else {
                console.log("! error type")
              }
            } else {
              console.log('error submit!!');
              return false;
            }
          }
      );
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    test(forName) {
      console.log(this.ruleForm)
    }
  }
}
</script>

<style>
.login-module {
  /*width: 380px;*/
  /*height: 325px;*/
  margin-top: 60px;
  /*border: none;*/
  position: absolute;
  right: 500px;
  text-align: center;
  width: 30%;
}

.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}
</style>