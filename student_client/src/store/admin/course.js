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
        /*offer(row) {
          const teacherId = sessionStorage.getItem("teacherId")
          const courseId = row.courseId
          const term = sessionStorage.getItem("currentTerm")

          const that = this
          axios.get(that.api.globalUrl + 'courseTeacher/insert/' + courseId + '/' + teacherId + '/' + term).then(function (resp) {
            if (resp.data === true) {
              that.$message({
                showClose: true,
                message: '开设成功',
                type: 'success'
              });
              window.location.reload()
            } else {
              that.$message({
                showClose: true,
                message: '开设失败，请联系管理员',
                type: 'error'
              });
            }
          });
        },*/
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
            //调用
            that.findAll();
        },
        editor(row) {
            this.$router.push({
                path: '/editorCourse',
                query: {
                    courseId: row.courseId
                }
            });
        },
        addForm(formName) {
            // this.$router.push({
            //   path: '/addCourse',
            //   query: {}
            // });
            this.dialogFormVisible = true;
        },
        addSubmitForm(formName) {
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
            rules: {
                cname: [
                    {required: true, message: '请输入名称', trigger: 'blur'},
                ],
                ccredit: [
                    {required: true, message: '请输入学分', trigger: 'change'},
                    {type: 'number', message: '请输入数字', trigger: 'blur'},
                ],
            }
            dialogFormVisible: false
        }
    },
    // props: {
    //   ruleForm: Object,
    //   isActive: Boolean
    // },
    watch: {
        ruleForm: {
            // handler(newRuleForm, oldRuleForm) {
            //   console.log("组件监听 form")
            //   console.log(newRuleForm)
            //   const that = this
            //   that.tmpList = null
            //   that.total = null
            //   that.tableData = null
            //   /*axios.post(that.api.globalUrl + "course/findBySearch", newRuleForm)
            //       .then((resp) => {
            //         console.log("查询结果:");
            //         console.log(resp)
            //         // that.tmpList = resp.data
            //         // that.total = resp.data.length
            //         // let start = 0, end = that.pageSize
            //         // let length = that.tmpList.length
            //         // let ans = (end < length) ? end : length
            //         // that.tableData = that.tmpList.slice(start, ans)
            //       });*/
            // },
            deep: true,
            immediate: true
        }
    },
}