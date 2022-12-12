package com.auggie.student_server.service;

import com.auggie.student_server.constant.MessageConstant;
import com.auggie.student_server.entity.Student;
import com.auggie.student_server.exception.ValidationException;
import com.auggie.student_server.mapper.StudentMapper;
import com.auggie.student_server.utils.ResultCode;
import com.auggie.student_server.utils.ResultUtils;
import com.auggie.student_server.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: auggie
 * @Date: 2022/2/9 08:27
 * @Description: StudentService
 * @Version 1.0.0
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public List<Student> findByPage(Integer num, Integer size) {
        // num：第几页，size：一页多大
        // num：从零开始
        List<Student> studentList = studentMapper.findAll();
        ArrayList<Student> list = new ArrayList<Student>();

        int start = size * num;
        int end = size * (num + 1);
        int sz = studentList.size();

        for (int i = start; i < end && i < sz; i++) {
            list.add(studentList.get(i));
        }

        return list;
    }

    public ResultUtils findBySearch(Student student) {
        try {
            Integer fuzzy = (student.getStudentPwd() == null) ? 0 : 1;
            List<Student> studentList = studentMapper.findBySearch(student, fuzzy);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), studentList);
        } catch (Exception e) {
            log.error("条件查询异常" + e);
            throw new ValidationException(MessageConstant.STUDENT_QUERY_FAIL);
        }
    }

    public Integer getLength() {
        return studentMapper.findAll().size();
    }

    /**
     * 根据学生id查询学生
     *
     * @param studentId
     * @return
     */
    public ResultUtils findById(Integer studentId) {
        if (StringUtils.isNull(studentId)) {
            throw new ValidationException(MessageConstant.STUDENT_ID_IS_NOT_EMPTY); //参数不能为空
        }
        //根据id查询学生
        Student currentStudent = studentMapper.findById(studentId);
        if (ObjectUtils.isEmpty(currentStudent)) {
            throw new ValidationException(MessageConstant.STUDENT_QUERY_FAIL);
        }
        return new ResultUtils(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), currentStudent);
    }

    public ResultUtils updateById(Student student) {
        try {
            //id不能为空
            if (StringUtils.isNull(student.getStudentId())) {
                throw new ValidationException(MessageConstant.STUDENT_ID_IS_NOT_EMPTY); //参数不能为空
            }
            studentMapper.updateById(student);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("更新学生信息失败" + e);
            throw new ValidationException(MessageConstant.UPDATE_STUDENT_FAIL);
        }
    }

    public ResultUtils save(Student student) {
        try {
            studentMapper.save(student);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("添加学习信息错误" + e);
            throw new ValidationException(MessageConstant.SAVE_STUDENT_FAIL);
        }
    }

    public ResultUtils deleteById(Integer studentId) {
        try {
            if (StringUtils.isNull(studentId)) {
                throw new ValidationException(MessageConstant.STUDENT_ID_IS_NOT_EMPTY); //参数不能为空
            }
            studentMapper.deleteById(studentId);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("添加学习信息错误" + e);
            throw new ValidationException(MessageConstant.DELETE_STUDENT_FAIL);
        }
    }

    @Override
    public ResultUtils<String> login(Student student) {
        if (StringUtils.isNull(student.getStudentId())) {
            throw new ValidationException(MessageConstant.STUDENT_ID_IS_NOT_EMPTY); //参数不能为空
        }
        Student currentStudent = studentMapper.findById(student.getStudentId());
        if (ObjectUtils.isEmpty(currentStudent)) {
            throw new ValidationException(MessageConstant.STUDENT_QUERY_FAIL);
        }
        //查询成功,密码不相等
        if (!student.getStudentPwd().equals(currentStudent.getStudentPwd())) {
            throw new ValidationException(MessageConstant.PLEASE_CHECK_PASSWORD);
        }
        return new ResultUtils<String>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
    }
}
