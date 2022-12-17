package com.auggie.student_server.service;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.utils.ResultUtils;

import java.util.Map;

/**
 * @Auther: auggie
 * @Date: 2022/2/9 13:46
 * @Description: CourseService
 * @Version 1.0.0
 */
public interface CourseService {


    /**
     * 条件查询
     *
     * @param map
     * @return
     */
    ResultUtils findBySearch(Map<String, String> map);


    /**
     * 根据id查询
     *
     * @param courseId
     * @return
     */
    ResultUtils findById(Integer courseId);

    /**
     * 更新课程
     *
     * @param course
     * @return
     */
    ResultUtils updateById(Course course);

    /**
     * 插入课程
     *
     * @param course
     * @return
     */
    ResultUtils insertCourse(Course course);

    /**
     * 删除课程
     *
     * @param courseId
     * @return
     */
    ResultUtils deleteById(Integer courseId);


    /**
     * 查询全部
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultUtils findAll(Integer pageNum, Integer pageSize);
}
