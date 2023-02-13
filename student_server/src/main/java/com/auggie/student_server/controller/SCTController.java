package com.auggie.student_server.controller;

import com.auggie.student_server.entity.CourseTeacherInfo;
import com.auggie.student_server.entity.SCTInfo;
import com.auggie.student_server.entity.StudentCourseTeacher;
import com.auggie.student_server.service.SCTService;
import com.auggie.student_server.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther:
 * @Date: 2022/2/10 20:15
 * @Description: SCTcontroller
 * @Version 1.0.0
 */

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/SCT")
public class SCTController {
    @Autowired
    private SCTService sCTService;

    @PostMapping("/save")
    public String save(@RequestBody StudentCourseTeacher studentCourseTeacher) {
        if (sCTService.isSCTExist(studentCourseTeacher)) {
            return "禁止重复选课";
        }
        System.out.println("正在保存 sct 记录：" + studentCourseTeacher);
        return sCTService.save(studentCourseTeacher) ? "选课成功" : "选课失败，联系管理员";
    }

    @ApiOperation("根据学生id查询学生选课情况")
    @GetMapping("/findCourseByStudentId/{studentId}/{term}")
    public ResultUtils findBySid(@PathVariable Integer studentId, @PathVariable String term) {
        return sCTService.findCourserInfoById(studentId, term);
    }

    @PostMapping("/findAll")
    public ResultUtils findAll(@RequestBody Map<String, Object> params) {
        return sCTService.findAll(params);
    }

    @GetMapping("/findAllTerm")
    public ResultUtils findAllTerm() {
        return sCTService.findAllTerm();
    }

    @PostMapping("/deleteBySCT")
    public boolean deleteBySCT(@RequestBody StudentCourseTeacher studentCourseTeacher) {
        System.out.println("正在删除 sct 记录：" + studentCourseTeacher);
        return sCTService.deleteBySCT(studentCourseTeacher);
    }

    @ApiOperation(value = "根据条件查询")
    @PostMapping("/findBySearch")
    public ResultUtils findBySearch(@RequestBody Map<String, String> map) {
        return sCTService.findBySearch(map);
    }

    @ApiOperation(value = "查询学生成绩")
    @PostMapping("/findGradeById")
    public ResultUtils findGradeById(@RequestBody CourseTeacherInfo courseTeacherInfo) {
        return sCTService.findGradeById(courseTeacherInfo);
    }

    @ApiOperation(value = "修改学生成绩")
    @PostMapping("/updateById")
    public ResultUtils updateById(@RequestBody Map<String, String> params) {
        return sCTService.updateById(params);
    }

    @ApiOperation(value = "删除学生成绩")
    @GetMapping("/deleteById/{studentId}/{courseId}/{teacherId}/{term}")
    public ResultUtils deleteById(@PathVariable Integer studentId,
                                  @PathVariable Integer courseId,
                                  @PathVariable Integer teacherId,
                                  @PathVariable String term) {
        return sCTService.deleteById(studentId, courseId, teacherId, term);
    }


}
