<template>
  <div>

    <el-container>
      <el-main>
        <el-form :inline="true" :model="ruleForm" ref="ruleForm" label-width="150px"
                 class="demo-ruleForm">
          <el-form-item label="课程号" prop="courseId">
            <el-input v-model.number="ruleForm.courseId"></el-input>
          </el-form-item>
          <el-form-item label="课程名" prop="courseName">
            <el-input v-model="ruleForm.courseName"></el-input>
          </el-form-item>
          <el-form-item label="学分下限" prop="lowBound">
            <el-input v-model.number="ruleForm.lowBound"></el-input>
          </el-form-item>
          <el-form-item label="学分上限" prop="highBound">
            <el-input v-model.number="ruleForm.highBound"></el-input>
          </el-form-item>
          <el-form-item label="模糊查询" prop="fuzzy">
            <el-switch v-model="ruleForm.fuzzy"></el-switch>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">查询</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
            <el-button type="primary" @click="addOrEditForm()">新增</el-button>
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
              prop="courseId"
              label="课程号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="courseName"
              label="课程名"
              width="180">
          </el-table-column>
          <el-table-column
              prop="courseCredit"
              label="学分"
              width="180">
          </el-table-column>
          <el-table-column
              label="操作"
              width="250">
            <template slot-scope="scope">
              <el-button @click="addOrEditForm(scope.row)" type="primary" size="small">编辑</el-button>
              <el-button slot="reference" @click="open(scope.row)" type="danger" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination

            layout="prev, pager, next"
            :total="total"
            :page-size="pageSize"
            @current-change="changePage"
        >
        </el-pagination>
      </el-main>

      <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
        <el-form style="width: 60%" :model="ruleAddForm" :rules="rules" ref="ruleForm" label-width="100px"
                 class="demo-ruleForm">
          <el-form-item label="课程名" prop="courseName">
            <el-input v-model="ruleAddForm.courseName"></el-input>
          </el-form-item>
          <el-form-item label="学分" prop="courseCredit">
            <el-input v-model.number="ruleAddForm.courseCredit"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="addSubmitForm('ruleForm')">确 定</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-container>

  </div>
</template>

<script>

export default {
  methods: {
    //删除确认框
    open(row) {
      this.$confirm('此操作将永久删除' + row.courseId + '号课程的信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteCourse(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //删除课程
    deleteCourse(row) {
      const that = this
      axios.get(that.api.globalUrl + 'course/deleteById/' + row.courseId)
          .then((resp) => {
            if ('000000' === resp.data.returnCode) {
              that.$message({
                showClose: true,
                message: '删除' + resp.data.returnMsg,
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
          }).catch(function (error) {
        that.$message({
          showClose: true,
          message: '删除出错，存在外键依赖',
          type: 'error'
        });
      }).finally(() => {
        that.findAll();
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //按条件查询
          this.findBySearch();
        } else {
          return;
        }
      });

    },
    // 按条件查找
    findBySearch() {
      axios.post(this.api.globalUrl + 'course/findBySearch', this.ruleForm)
          .then((resp) => {
            if ('000000' === resp.data.returnCode) {
              this.tableData = resp.data.data;
            } else {
              this.$message({
                showClose: true,
                message: resp.data.returnMsg,
                type: 'error'
              });
            }
          });
    },
    // 查询全部课程
    findAll() {
      const that = this;
      //查询全部课程
      axios.get(that.api.globalUrl + 'course/findAll/' + that.pageNum + '/' + that.pageSize)
          .then((resp) => {
            if ('000000' === resp.data.returnCode) {
              that.tableData = resp.data.data.list;
              that.total = resp.data.data.total;
            } else {
              that.$message({
                showClose: true,
                message: resp.data.returnMsg,
                type: 'error'
              });
            }
          });
    },
    changePage(pageNum) {
      const that = this;
      that.pageNum = pageNum
      that.findAll();
    },
    // 新增和编辑
    addOrEditForm(row) {
      if (row) {
        this.ruleAddForm = JSON.parse(JSON.stringify(row));
        console.log("编辑课程", this.ruleAddForm)
        this.dialogTitle = '编辑课程';
      } else {
        this.dialogTitle = '新增课程';
        this.ruleAddForm = {};
      }
      this.dialogFormVisible = true;
    },
    // 新增或修改
    addSubmitForm(formName) {

      this.$refs[formName].validate((valid) => {
        if (valid) {
          var courseId = this.ruleAddForm.courseId;
          if (courseId === null || "" === courseId || undefined === courseId) {
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
      const that = this
      axios.post(that.api.globalUrl + "course/save", that.ruleAddForm).then(function (resp) {
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
      }).finally(() => {
        that.dialogFormVisible = false;
        that.findAll();
      });
    },
    //修改
    edit() {
      const that = this;
      axios.post(that.api.globalUrl + "course/updateCourse", this.ruleAddForm)
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
          }).finally(() => {
        that.dialogFormVisible = false;
        that.findAll();
      });
    },
    resetForm(formName) {
      const that = this;
      that.$refs[formName].resetFields();
    },
  },
  created() {
    this.findAll();
  },
  data() {
    return {
      tableData: null,
      pageSize: 10,
      pageNum: 1,
      total: null,
      tmpList: null,
      type: sessionStorage.getItem("type"),
      ruleForm: {
        courseId: '',
        courseName: '',
        courseCredit: '',
        highBound: '',
        lowBound: '',
        fuzzy: true
      },
      ruleAddForm: {
        courseName: '',
        courseCredit: '',
        courseId: ''
      },
      rules: {
        courseName: [
          {required: true, message: '请输入名称', trigger: 'blur'},
        ],
        courseCredit: [
          {required: true, message: '请输入学分', trigger: 'change'},
          {type: 'number', message: '请输入数字', trigger: 'blur'},
        ],
      },
      dialogFormVisible: false,
      dialogTitle: ""
    }
  },

}

</script>