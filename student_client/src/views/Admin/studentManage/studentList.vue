<template>
  <div>

    <el-container>
      <el-main>
        <!--        <el-card>-->
        <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
                 class="demo-ruleForm">
          <el-form-item label="学号" prop="studentId">
            <el-input v-model.number="ruleForm.studentId"></el-input>
          </el-form-item>
          <el-form-item label="学生姓名" prop="studentName">
            <el-input v-model="ruleForm.studentName"></el-input>
          </el-form-item>
          <el-form-item label="模糊查询" prop="studentPwd">
            <el-switch v-model="ruleForm.studentPwd"></el-switch>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">查询</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
            <el-button type="primary" @click="addOrEditor()">新增</el-button>
          </el-form-item>
        </el-form>
        <!--        </el-card>-->
        <router-view></router-view>
      </el-main>
    </el-container>

    <el-container>
      <el-main>
        <el-table
            :data="tableData"
            style="width: 100%">
          <el-table-column

              prop="studentId"
              label="学号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="studentName"
              label="姓名"
              width="180">
          </el-table-column>
          <el-table-column
              prop="studentPwd"
              label="密码"
              width="180">
          </el-table-column>
          <el-table-column
              prop="creatTime"
              label="创建时间"
              width="180">
          </el-table-column>
          <el-table-column
              label="操作"
              width="300">
            <template slot-scope="scope">
              <!--                            <el-popconfirm
                                              confirm-button-text='删除'
                                              cancel-button-text='取消'
                                              icon="el-icon-info"
                                              icon-color="red"
                                              title="删除不可复原"
                                              @confirm="deleteStudent(scope.row)"
                                          >
                            <el-button slot="reference" type="danger" size="mini" @click="open(scope.row)">删除</el-button>
                                          </el-popconfirm>-->
              <el-button @click="addOrEditor(scope.row)" type="primary" size="mini">编辑</el-button>
              <el-button type="danger" size="mini" @click="open(scope.row)">删除</el-button>
            </template>
          </el-table-column>

        </el-table>

        <el-pagination
            background

            layout="prev, pager, next"
            :total="total"
            :page-size="pageSize"
            @current-change="changePage"
        >
        </el-pagination>
      </el-main>

      <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
        <el-form style="width: 60%" :model="editRuleForm" :rules="rules" ref="ruleForm" label-width="100px"
                 class="demo-ruleForm">
          <el-form-item label="学生姓名" prop="studentName">
            <el-input v-model="editRuleForm.studentName"></el-input>
          </el-form-item>
          <el-form-item label="初始密码" prop="studentPwd">
            <el-input v-model="editRuleForm.studentPwd" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="editSubmitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

    </el-container>

  </div>
</template>

<script>
export default {

  data() {
    return {
      tableData: {},
      pageSize: 7,
      pageNum: 1,
      total: 0,
      ruleForm: {
        studentId: "",
        studentName: "",
        studentPwd: true
      },
      editRuleForm: {
        studentId: "",
        studentName: "",
        studentPwd: ''
      },
      dialogTitle: '', // 表单title
      dialogFormVisible: false, // 弹框
      tmpList: {},
      rules: {
        studentId: [
          {type: 'number', message: '必须是数字类型'}
        ],
        studentName: [],
      }
    }
  },

  created() {
    //查询全部
    this.findAll();

  },

  methods: {
    //删除确认框
    open(row) {
      this.$confirm('此操作将永久删除' + row.studentId + '号学生的信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteStudent(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //删除学生
    deleteStudent(row) {
      const that = this
      axios.get(that.api.globalUrl + 'student/deleteById/' + row.studentId)
          .then((resp) => {
            if ("000000" === resp.data.returnCode) {
              that.$message({
                showClose: true,
                message: '删除' + resp.data.returnMsg,
                type: 'success'
              });
            } else {
              that.$message({
                showClose: true,
                message: resp.data.returnMsg,
                type: 'error'
              });
            }
          }).catch(function (e) {
        that.$message({
          showClose: true,
          message: '删除出错',
          type: 'error'
        });
      }).finally(() => {
        //重新获取数据
        that.findAll();
      })
    },
    //分页
    changePage(pageNum) {
      this.pageNum = pageNum;
      this.findAll();
    },
    //条件查询
    findSearch() {
      axios.post(this.api.globalUrl + 'student/findBySearch', this.ruleForm)
          .then((resp) => {
            const that = this;
            if ('000000' !== resp.data.returnCode) {
              console.log("查询失败")
              this.$message.error(resp.data.returnMsg);
              return;
            }
            console.log('获取查询数据：')
            that.tmpList = resp.data.data;
            that.total = resp.data.data.length;
            let start = 0, end = that.pageSize
            let length = that.tmpList.length
            let ans = end < length ? end : length
            that.tableData = that.tmpList.slice(start, ans)
          });
    },
    //修改
    addOrEditor(row) {
      if (row) {
        this.editRuleForm = JSON.parse(JSON.stringify(row));
        this.dialogTitle = '编辑学生';
      } else {
        this.dialogTitle = '新增学生';
        this.editRuleForm = {};
      }
      this.dialogFormVisible = true;
    },
    // 新增或修改
    editSubmitForm(formName) {

      this.$refs[formName].validate((valid) => {
        if (valid) {
          const studentId = this.editRuleForm.studentId;
          if (studentId === null || "" === studentId || undefined === studentId) {
            this.save(); // 新增
          } else {
            this.edit(); // 保存
          }
        } else {
          return false;
        }
      });
    },
    // 新增
    save() {
      const that = this;
      axios.post(this.api.globalUrl + "student/addStudent", this.ruleForm)
          .then((resp) => {
            if ("000000" === resp.data.returnCode) {
              that.$message({
                showClose: true,
                message: resp.data.returnMsg,
                type: 'success'
              });
            } else {
              that.$message.error(resp.data.returnMsg);
            }
          }).finally(() => {
        that.dialogFormVisible = false;
        that.findAll();
      });
    },
    // 编辑
    edit() {
      // 通过前端校验
      const that = this
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
          }).finally(() => {
        that.dialogFormVisible = false;
        that.findAll();
      });
    },
    //查询全部
    findAll() {
      const that = this;
      //分页查询
      axios.get(this.api.globalUrl + 'student/findByPage/' + that.pageNum + '/' + that.pageSize)
          .then((resp) => {
            if ("000000" === resp.data.returnCode) {
              that.tableData = resp.data.data.list;
              that.total = resp.data.data.total;
            } else {
              that.$message.error(resp.data.returnMsg)
            }
          });
    },
    // 条件查询
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.ruleForm.studentPwd === true) {
            this.ruleForm.studentPwd = 'fuzzy'
          } else {
            this.ruleForm.studentPwd = null
          }

          this.findSearch();

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    //重置
    resetForm(formName) {
      const that =this;
      that.$refs[formName].resetFields();
    }
  },
}
</script>