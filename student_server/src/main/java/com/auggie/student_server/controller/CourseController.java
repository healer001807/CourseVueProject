package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.service.CourseService;
import com.auggie.student_server.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther:
 * @Date: 2022/2/9 13:45
 * @Description: CourseController
 * @Version 1.0.0
 */
@Api("课程控制层")
@RestController
@CrossOrigin("*")
@RequestMapping("/course")
@Slf4j
public class CourseController {
    @Autowired
    private CourseService courseService;

    @ApiOperation("条件查询")
    @PostMapping("/findBySearch")
    public ResultUtils findBySearch(@RequestBody Map<String, String> map) {
        return courseService.findBySearch(map);
    }

    @ApiOperation("根据id查询课程")
    @GetMapping("/findById/{courseId}")
    public ResultUtils findById(@PathVariable("courseId") Integer courseId) {
        return courseService.findById(courseId);
    }

    @ApiOperation("保存课程")
    @PostMapping("/save")
    public ResultUtils save(@RequestBody Course course) {
        log.info("保存课程数据：：" + course);
        return courseService.insertCourse(course);
    }

    @ApiOperation("删除课程")
    @GetMapping("/deleteById/{courseId}")
    public ResultUtils deleteById(@PathVariable("courseId") Integer courseId) {
        log.info("正在删除课程 courseId: " + courseId);
        return courseService.deleteById(courseId);
    }

    @ApiOperation("更新课程")
    @PostMapping("/updateCourse")
    public ResultUtils updateCourse(@RequestBody Course course) {
        log.info("正在修改课程: " + course);
        return courseService.updateById(course);
    }

    @ApiOperation("查询全部课程")
    @GetMapping("findAll/pageNum/pageSize")
    public ResultUtils findAll(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        return courseService.findAll(pageNum, pageSize);
    }

}
