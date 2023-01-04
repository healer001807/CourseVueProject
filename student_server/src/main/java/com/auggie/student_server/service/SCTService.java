package com.auggie.student_server.service;

import com.auggie.student_server.entity.CourseTeacherInfo;
import com.auggie.student_server.entity.SCTInfo;
import com.auggie.student_server.entity.StudentCourseTeacher;
import com.auggie.student_server.mapper.StudentCourseTeacherMapper;
import com.auggie.student_server.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: auggie
 * @Date: 2022/2/10 20:10
 * @Description: SCTService
 * @Version 1.0.0
 */

@Service
public interface SCTService {


    List<CourseTeacherInfo> findBySid(Integer sid, String term);

    /**
     * 查询所有学期
     *
     * @return
     */
    ResultUtils findAllTerm();

    boolean isSCTExist(StudentCourseTeacher studentCourseTeacher);


    boolean save(StudentCourseTeacher studentCourseTeacher);

    boolean deleteBySCT(StudentCourseTeacher studentCourseTeacher);

    boolean deleteById(Integer sid, Integer cid, Integer tid, String term);

    SCTInfo findByIdWithTerm(Integer sid, Integer cid, Integer tid, String term);

    /**
     * 修改成绩
     *
     * @param params
     * @return
     */
    ResultUtils updateById(Map<String, String> params);

    List<SCTInfo> findBySearch(Map<String, String> map);

    /**
     * 查询当前学期的成绩
     *
     * @param params
     * @return
     */
    ResultUtils findAll(Map<String, Object> params);

    /**
     * 查询成绩
     *
     * @param courseTeacherInfo
     * @return
     */
    ResultUtils findGradeById(CourseTeacherInfo courseTeacherInfo);
}
