package com.auggie.student_server.service;

import com.auggie.student_server.entity.Student;
import com.auggie.student_server.mapper.StudentMapper;
import com.auggie.student_server.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: auggie
 * @Date: 2022/2/9 08:27
 * @Description: StudentService
 * @Version 1.0.0
 */

public interface StudentService {

    public List<Student> findByPage(Integer num, Integer size);

    public List<Student> findBySearch(Integer sid, String sname, Integer fuzzy);

    public Integer getLength();

    /**
     * 根据学生id查询学生
     *
     * @param studentId
     * @return
     */
    public ResultUtils findById(Integer studentId);

    public boolean updateById(Student student);

    public boolean save(Student student);

    public boolean deleteById(Integer sid);

    /**
     * 学生登录
     *
     * @param student
     * @return
     */
    ResultUtils login(Student student);
}
