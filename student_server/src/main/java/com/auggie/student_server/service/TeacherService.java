package com.auggie.student_server.service;

import com.auggie.student_server.entity.Student;
import com.auggie.student_server.entity.Teacher;
import com.auggie.student_server.mapper.StudentMapper;
import com.auggie.student_server.mapper.TeacherMapper;
import com.auggie.student_server.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther:
 * @Date: 2022/2/9 10:55
 * @Description: TeacherService
 * @Version 1.0.0
 */

@Service
public interface TeacherService {

    /**
     * 条件查找
     *
     * @param map
     * @return
     */
    ResultUtils findBySearch(Map<String, String> map);

    /**
     * 分页查找
     *
     * @param num
     * @param size
     * @return
     */
    ResultUtils findByPage(Integer num, Integer size);

    /**
     * 根据id查找
     *
     * @param teacherId
     * @return
     */
    ResultUtils findById(Integer teacherId);

    /**
     * 更新老师信息
     *
     * @param teacher
     * @return
     */
    ResultUtils updateById(Teacher teacher);

    /**
     * 保存老师信息
     *
     * @param teacher
     * @return
     */
    ResultUtils save(Teacher teacher);

    /**
     * 删除老师信息
     *
     * @param tid
     * @return
     */
    ResultUtils deleteById(Integer tid);

    /**
     * 教师登录
     *
     * @param teacher
     * @return
     */
    ResultUtils login(Teacher teacher);
}
