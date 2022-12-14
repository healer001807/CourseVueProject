package com.auggie.student_server.service;

import com.auggie.student_server.entity.Student;
import com.auggie.student_server.utils.ResultUtils;

/**
 * @Auther:
 * @Date: 2022/2/9 08:27
 * @Description: StudentService
 * @Version 1.0.0
 */

public interface StudentService {

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultUtils findByPage(Integer pageNum, Integer pageSize);

    /**
     * 条件查询
     *
     * @param student
     * @return
     */
    ResultUtils findBySearch(Student student);


    /**
     * 根据学生id查询学生
     *
     * @param studentId
     * @return
     */
    ResultUtils findById(Integer studentId);

    /**
     * 更新学生信息
     *
     * @param student
     * @return
     */
    ResultUtils updateById(Student student);

    /**
     * 保存学生
     *
     * @param student
     * @return
     */
    ResultUtils save(Student student);

    /**
     * 删除学生信息
     *
     * @param sid
     * @return
     */
    ResultUtils deleteById(Integer sid);

    /**
     * 学生登录
     *
     * @param student
     * @return
     */
    ResultUtils login(Student student);
}
