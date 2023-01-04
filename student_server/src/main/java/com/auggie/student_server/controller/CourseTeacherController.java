package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.entity.CourseTeacher;
import com.auggie.student_server.service.CourseTeacherService;
import com.auggie.student_server.utils.ResultUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: auggie
 * @Date: 2022/2/10 16:51
 * @Description: CourseTeacherController
 * @Version 1.0.0
 */

@RestController
@CrossOrigin("*")
@RequestMapping("/courseTeacher")
public class CourseTeacherController {
    @Autowired
    private CourseTeacherService courseTeacherService;

    @GetMapping("/insert/{cid}/{tid}/{term}")
    public boolean insert(@PathVariable Integer cid, @PathVariable Integer tid, @PathVariable String term) {
        if (courseTeacherService.findBySearch(cid, tid, term).size() != 0) {
            return false;
        }
        return courseTeacherService.insertCourseTeacher(cid, tid, term);
    }

    @GetMapping("/findMyCourse/{tid}/{term}")
    public List<Course> findMyCourse(@PathVariable Integer tid, @PathVariable String term) {
        System.out.println("查询教师课程：" + tid + " " + term);
        return courseTeacherService.findMyCourse(tid, term);
    }

    @PostMapping("/findCourseTeacherInfo")
    public ResultUtils findCourseTeacherInfo(@RequestBody Map<String, String> map) {
        return courseTeacherService.findCourseTeacherInfo(map);
    }

    @PostMapping("/deleteById")
    public ResultUtils deleteById(@RequestBody CourseTeacher courseTeacher) {
        return courseTeacherService.deleteById(courseTeacher);
    }

    @GetMapping("/testDate")
    public ResultUtils testDate() throws ParseException {

        Map<String,Object> dataMap = new HashMap<>();

        String time ="2022-02-28";
        LocalDate localDate = LocalDate.parse(time).plusMonths(1);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        String format = dateTimeFormatter.format(localDate);
        dataMap.put("localDate",format);
        System.out.println(format);
         time ="2022-02-28 10:10:10";
        Date date = DateUtils.parseDate(time, "yyyy-MM-dd");
        LocalDate parse = LocalDate.parse(time);
        System.out.println(parse);
        //format = LocalDateTime.parse(date).plusMonths(6).format(dateTimeFormatter);
//        dataMap.put("localDateTime",format);
        return ResultUtils.success("0000","22",format);
    }

}
