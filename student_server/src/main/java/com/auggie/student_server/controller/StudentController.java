package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Student;
import com.auggie.student_server.service.StudentService;
import com.auggie.student_server.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther:
 * @Date: 2022/2/8 17:37
 * @Description: StudentController
 * @Version 1.0.0
 */

@Api("学生控制层")
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "添加学生信息")
    @PostMapping("/addStudent")
    public ResultUtils addStudent(@RequestBody Student student) {
        log.info("正在保存学生对象" + student);
        return studentService.save(student);
    }

    @ApiOperation("学生登录")
    @PostMapping("/login")
    public ResultUtils login(@RequestBody Student student) {
        log.info("正在验证学生登陆 " + student);
        return studentService.login(student);
    }

    @ApiOperation("条件查询学生信息")
    @PostMapping("/findBySearch")
    public ResultUtils findBySearch(@RequestBody Student student) {
        return studentService.findBySearch(student);
    }

    @ApiOperation(value = "根据id查询学生信息")
    @GetMapping("/findById/{studentId}")
    public ResultUtils findById(@PathVariable("studentId") Integer studentId) {
        log.info("正在查询学生信息 By id " + studentId);
        return studentService.findById(studentId);
    }

    @ApiOperation("分页查询学生信息")
    @GetMapping("/findByPage/{pageNum}/{pageSize}")
    public ResultUtils findByPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        log.info("查询学生列表分页 " + pageNum + " " + pageSize);
        return studentService.findByPage(pageNum, pageSize);
    }

    @GetMapping("/getLength")
    public Integer getLength() {
        return studentService.getLength();
    }

    @ApiOperation("删除学生信息")
    @GetMapping("/deleteById/{studentId}")
    public ResultUtils deleteById(@PathVariable("studentId") int studentId) {
        log.info("正在删除学生 studentId：" + studentId);
        return studentService.deleteById(studentId);
    }

    @ApiOperation("更新学生信息")
    @PostMapping("/updateStudent")
    public ResultUtils updateStudent(@RequestBody Student student) {
        log.info("更新 " + student);
        return studentService.updateById(student);
    }
}
