package com.auggie.student_server.service;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.entity.CourseTeacher;
import com.auggie.student_server.utils.ResultUtils;

import java.util.List;
import java.util.Map;

/**
 * @Auther:
 * @Date:
 * @Description: CourseTeacherService
 * @Version 1.0.0
 */


public interface CourseTeacherService {
    boolean insertCourseTeacher(Integer cid, Integer tid, String term);

    List<Course> findMyCourse(Integer tid, String term);

    /**
     * 根据教师编号查新课程
     *
     * @param map
     * @return
     */
    ResultUtils findCourseTeacherInfo(Map<String, String> map);

    List<CourseTeacher> findBySearch(Integer cid, Integer tid, String term);

    List<CourseTeacher> findBySearch(Map<String, String> map);

    /**
     * 删除教师课设课程
     *
     * @param courseTeacher
     * @return
     */
    ResultUtils deleteById(CourseTeacher courseTeacher);
}
