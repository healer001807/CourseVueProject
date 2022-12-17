<template>
  <div>

    <el-container>
      <el-main>
        <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
                 class="demo-ruleForm">
          <el-form-item label="工号" prop="teacherId">
            <el-input v-model.number="ruleForm.teacherId"></el-input>
          </el-form-item>
          <el-form-item label="教师姓名" prop="teacherName">
            <el-input v-model="ruleForm.teacherName"></el-input>
          </el-form-item>
          <el-form-item label="模糊查询" prop="fuzzy">
            <el-switch v-model="ruleForm.fuzzy"></el-switch>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">查询</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
            <el-button type="primary" @click="addForm()">新增</el-button>
          </el-form-item>
        </el-form>
        <router-view></router-view>

      </el-main>
    </el-container>

    <el-container>
      <el-main>

        <el-table
            :data="tableData"
            style="width: 100%">
          <el-table-column
              prop="teacherId"
              label="工号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="teacherName"
              label="姓名"
              width="180">
          </el-table-column>
          <el-table-column
              prop="teacherPwd"
              label="密码"
              width="150">
          </el-table-column>
          <el-table-column
              prop="createTime"
              label="创建时间"
              width="180">
          </el-table-column>
          <el-table-column
              label="操作"
              width="250">
            <template slot-scope="scope">
              <el-button @click="editor(scope.row)" type="primary" size="small">编辑</el-button>
              <el-button slot="reference" type="danger" @click="open(scope.row)" size="small">删除</el-button>
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
    </el-container>


  </div>
</template>

<script>
export default {

  data() {
    return {
      tableData: null,
      pageSize: 10,
      pageNum: 1,
      total: null,
      tmpList: null,
      rules: {
        // teacherId: [
        //   {type: 'number', message: '必须是数字类型'}
        // ],
      },
      ruleForm: {
        teacherId: '',
        teacherName: '',
        fuzzy: true,
      }
    }
  },
  methods: {
    //查找所有
    findAll() {
      debugger;
      const that = this;
      axios.post(that.api.globalUrl + 'teacher/findByPage/' + that.pageNum + '/' + that.pageSize)
          .then((resp) => {
            if ('000000' === resp.data.returnCode) {
              that.tableData = resp.data.data.list;
            } else {
              that.$message.error(resp.data.returnMsg);
            }
          });
    },
    //换页
    changePage(pageNum) {
      this.findAll();
    },
    //条件查询
    submitForm(formName) {
      debugger;
      const that = this;
      that.$refs[formName].validate((valid) => {
        if (valid) {
          debugger;
          if (that.ruleForm.teacherName === '' || that.ruleForm.teacherName === undefined) {
            that.fuzzy = false;
            that.ruleForm.fuzzy = that.fuzzy;
          }
          axios.post(that.api.globalUrl + '/teacher/findBySearch', that.ruleForm)
              .then((resp) => {
                if ('000000' === resp.data.returnCode) {
                  that.tableData = resp.data.data;
                } else {
                  that.$message.error(resp.data.returnMsg);
                }
              }).catch((e) => {
            that.$message.error(resp.data.returnMsg);
          });

        }
      });
    },
    //新增
    addForm() {
      this.$router.push({
        path: '/addTeacher',
        query: {}
      })
    },
    //编辑
    editor(row) {
      if (row.teacherName === 'admin') {
        this.$message({
          showClose: true,
          message: 'admin 不可编辑',
          type: 'error'
        });
        return
      }
      this.$router.push({
        path: '/editorTeacher',
        query: {
          teacherId: row.teacherId
        }
      })
    },
    //删除确认框
    open(row) {
      this.$confirm('此操作将永久删除' + row.teacherId + '号教师的信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteTeacher(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //删除教师
    deleteTeacher(row) {
      if (row.teacherName === 'admin') {
        this.$message({
          showClose: true,
          message: 'admin 不可删除',
          type: 'error'
        });
        return
      }
      const that = this
      axios.get(this.api.globalUrl + 'teacher/deleteById/' + row.teacherId)
          .then((resp) => {
            if ('000000' === resp.data.returnCode) {
              that.$message({
                showClose: true,
                message: resp.data.returnMsg,
                type: 'success'
              });
              // window.location.reload()
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
          message: '删除出错，存在外键依赖',
          type: 'error'
        });
      })
    },
    //重置
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },

  created() {
    this.findAll();
  },

  // props: {
  //   ruleForm: {
  //     teacherId: "",
  //     teacherName: "",
  //     teacherPwd: ""
  //   }
  // },
  watch: {
    // ruleForm: {
    //   handler(newRuleForm, oldRuleForm) {
    //     console.log("组件监听 form")
    //     const that = this
    //     that.tmpList = null
    //     that.total = null
    //     that.tableData = null
    //     axios.post(this.api.globalUrl + "teacher/findBySearch", newRuleForm)
    //         .then(function (resp) {
    //           console.log("查询结果:" + resp);
    //           that.tmpList = resp.data.data
    //           that.total = resp.data.data.length
    //           let start = 0, end = that.pageSize
    //           let length = that.tmpList.length
    //           let ans = (end < length) ? end : length
    //           that.tableData = that.tmpList.slice(start, end)
    //         })
    //   },
    //   deep: true,
    //   immediate: true
    // }
  },
}
</script>