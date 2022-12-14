package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: auggie
 * @Date: 2022/2/9 10:51
 * @Description: TeacherMapper
 * @Version 1.0.0
 */

@Repository
@Mapper
public interface TeacherMapper {
    /**
     * 查询所有教师
     *
     * @return
     */
    List<Teacher> findAll();

    /**
     * 根据id查询教师
     *
     * @param teacherId
     * @return
     */
    Teacher findById(@Param("teacherId") Integer teacherId);

    /**
     * 按条件查找
     *
     * @param teacher
     * @param fuzzy
     * @return
     */
    List<Teacher> findBySearch(@Param("teacher") Teacher teacher, @Param("fuzzy") Integer fuzzy);

    /**
     * 更新教师
     *
     * @param teacher
     * @return
     */
    boolean updateById(Teacher teacher);

    /**
     * 添加教师信息
     *
     * @param teacher
     * @return
     */
    boolean save(Teacher teacher);

    /**
     * 删除教师信息
     *
     * @param teacherId
     * @return
     */
    boolean deleteById(@Param("teacherId") Integer teacherId);

}
