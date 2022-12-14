package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Teacher;
import com.auggie.student_server.service.TeacherService;
import com.auggie.student_server.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther:
 * @Date: 2022/2/9 11:02
 * @Description: TeacherController
 * @Version 1.0.0
 */

@Api("教师控制层")
@RestController
@CrossOrigin("*")
@RequestMapping("/teacher")
public class TeacherController {
    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "添加教师")
    @PostMapping("/addTeacher")
    public ResultUtils addTeacher(@RequestBody Teacher teacher) {
        return teacherService.save(teacher);
    }

    @ApiOperation("教师登录")
    @PostMapping("/login")
    public ResultUtils login(@RequestBody Teacher teacher) {
        logger.info("正在验证教师登陆 " + teacher);
        return teacherService.login(teacher);
    }

    @ApiOperation("根据id查询教师")
    @GetMapping("/findById/{teacherId}")
    public ResultUtils findById(@PathVariable("teacherId") Integer teacherId) {
        logger.info("正在查询学生信息 By id " + teacherId);
        return teacherService.findById(teacherId);
    }

    @ApiOperation("条件查询")
    @PostMapping("/findBySearch")
    public ResultUtils findBySearch(@RequestBody Map<String, String> map) {
        return teacherService.findBySearch(map);
    }

    @ApiOperation("删除教师")
    @GetMapping("/deleteById/{teacherId}")
    public ResultUtils deleteById(@PathVariable("teacherId") int teacherId) {
        logger.info("正在删除学生 tid：" + teacherId);
        return teacherService.deleteById(teacherId);
    }

    @ApiOperation("更新教师")
    @PostMapping("/updateTeacher")
    public ResultUtils updateTeacher(@RequestBody Teacher teacher) {
        logger.info("更新 " + teacher);
        return teacherService.updateById(teacher);
    }

    @ApiOperation("分页查找")
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public ResultUtils findByPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        logger.info("分页查找教师==>{}" + pageNum + "==>{}" + pageSize);
        return teacherService.findByPage(pageNum, pageSize);
    }
}
