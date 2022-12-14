package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther:
 * @Date: 2022/2/8 16:12
 * @Description: StudentMapper
 * @Version 1.0.0
 */

@Mapper
@Repository
public interface StudentMapper {

    /**
     * 查询所有学生信息
     *
     * @return
     */
    List<Student> findAll();

    /**
     * 根据学生id查询学生
     *
     * @param studentId
     * @return
     */
    Student findById(@Param("studentId") Integer studentId);

    /**
     * 条件查询学生信息
     *
     * @param student
     * @param fuzzy
     * @return
     */
    List<Student> findBySearch(@Param("student") Student student, @Param("fuzzy") Integer fuzzy);

    /**
     * 更新学生信息
     *
     * @param student
     * @return
     */
    boolean updateById(Student student);

    /**
     * 保存学生
     *
     * @param student
     * @return
     */
    boolean save(Student student);

    /**
     * 删除学生信息
     *
     * @param studentId
     * @return
     */
    boolean deleteById(@Param("studentId") Integer studentId);

}
