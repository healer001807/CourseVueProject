package com.auggie.student_server.controller;

import com.auggie.student_server.entity.CourseTeacherInfo;
import com.auggie.student_server.entity.SCTInfo;
import com.auggie.student_server.entity.StudentCourseTeacher;
import com.auggie.student_server.service.SCTService;
import com.auggie.student_server.utils.ResultUtils;
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

    @GetMapping("/findBySid/{{term}")
    public List<CourseTeacherInfo> findBySid(@PathVariable Integer sid, @PathVariable String term) {
        return sCTService.findBySid(sid, term);
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

    @PostMapping("/findBySearch")
    public List<SCTInfo> findBySearch(@RequestBody Map<String, String> map) {
        return sCTService.findBySearch(map);
    }

    @PostMapping("/findGradeById")
    public ResultUtils findGradeById(@RequestBody CourseTeacherInfo courseTeacherInfo) {
        return sCTService.findGradeById(courseTeacherInfo);
    }

    @PostMapping("/updateById")
    public ResultUtils updateById(@RequestBody Map<String, String> params) {
        return sCTService.updateById(params);
    }

    @GetMapping("/deleteById/{sid}/{cid}/{tid}/{term}")
    public boolean deleteById(@PathVariable Integer sid,
                              @PathVariable Integer cid,
                              @PathVariable Integer tid,
                              @PathVariable String term) {
        return sCTService.deleteById(sid, cid, tid, term);
    }


}
