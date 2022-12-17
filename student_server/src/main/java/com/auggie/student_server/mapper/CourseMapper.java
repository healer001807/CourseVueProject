package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: auggie
 * @Date: 2022/2/9 13:30
 * @Description: CourseMapper
 * @Version 1.0.0
 */

@Repository
@Mapper
public interface CourseMapper {
    /**
     * 条件查询课程
     *
     * @param course
     * @param fuzzy
     * @param lowBound
     * @param highBound
     * @return
     */
    List<Course> findBySearch(@Param("course") Course course, @Param("fuzzy") Integer fuzzy,
                              @Param("lowBound") Integer lowBound, @Param("highBound") Integer highBound);

    /**
     * 保存课程
     *
     * @param course
     * @return
     */
    boolean insertCourse(Course course);

    /**
     * 更新课程
     *
     * @param course
     * @return
     */
    boolean updateById(@Param("course") Course course);

    /**
     * 删除课程
     *
     * @param courseId
     * @return
     */
    boolean deleteById(@Param("courseId") Integer courseId);

    /**
     * 查询全部课程
     *
     * @return
     */
    List<Course> findAll();
}
