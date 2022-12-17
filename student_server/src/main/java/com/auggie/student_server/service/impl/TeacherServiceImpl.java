package com.auggie.student_server.service.impl;

import com.auggie.student_server.constant.MessageConstant;
import com.auggie.student_server.entity.Teacher;
import com.auggie.student_server.exception.ValidationException;
import com.auggie.student_server.mapper.TeacherMapper;
import com.auggie.student_server.service.TeacherService;
import com.auggie.student_server.utils.ResultCode;
import com.auggie.student_server.utils.ResultUtils;
import com.auggie.student_server.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther:
 * @Date: 2022/2/9 10:55
 * @Description: TeacherService
 * @Version 1.0.0
 */

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public ResultUtils findBySearch(Map<String, String> map) {
        try {
            Teacher teacher = new Teacher();
            Integer fuzzy = null;
            if (map.containsKey("teacherId")) {
                String teacherId = map.get("teacherId");
                if (StringUtils.isNotEmpty(teacherId)) {
                    teacher.setTeacherId(Integer.valueOf(teacherId));
                }
            }
            if (map.containsKey("teacherName")) {
                if (StringUtils.isNotEmpty(map.get("teacherName"))) {
                    teacher.setTeacherName(map.get("teacherName"));
                }
            }
            if (map.containsKey("fuzzy")) {
                fuzzy = "true".equals(map.get("fuzzy")) ? 1 : 0;
            }

            List<Teacher> teachers = teacherMapper.findBySearch(teacher, fuzzy);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), teachers);
        } catch (Exception e) {
            log.error("查询失败", e);
            throw new ValidationException(MessageConstant.QUERY_STUDENT_FAIL);
        }
    }

    @Override
    public ResultUtils findByPage(Integer pageNum, Integer pageSize) {

        try {
            if (StringUtils.isNull(pageNum)) {
                pageNum = 1;
            }
            if (StringUtils.isNull(pageSize)) {
                pageSize = 10;
            }
            PageHelper.startPage(pageNum, pageSize);
            List<Teacher> teacherList = teacherMapper.findAll();

            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), new PageInfo(teacherList));
        } catch (Exception e) {
            log.error("查询失败", e);
            throw new ValidationException(MessageConstant.QUERY_TEACHER_FAIL);
        }
    }


    @Override
    public ResultUtils findById(Integer teacherId) {
        try {
            if (StringUtils.isNull(teacherId)) {
                throw new ValidationException(MessageConstant.TEACHER_ID_IS_NOT_EMPTY);
            }
            Teacher teacher = teacherMapper.findById(teacherId);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), teacher);
        } catch (Exception e) {
            log.error("查询教师失败" + e);
            throw new ValidationException(MessageConstant.QUERY_TEACHER_FAIL);
        }
    }

    @Override
    public ResultUtils updateById(Teacher teacher) {
        try {
            if (StringUtils.isNull(teacher.getTeacherId())) {
                throw new ValidationException(MessageConstant.TEACHER_ID_IS_NOT_EMPTY);
            }
            teacherMapper.updateById(teacher);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("更新教师失败" + e);
            throw new ValidationException(MessageConstant.UPDATE_TEACHER_FAIL);
        }
    }

    @Override
    public ResultUtils save(Teacher teacher) {
        try {
            teacherMapper.save(teacher);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("保存教师信息异常", e);
            throw new ValidationException(MessageConstant.SAVE_TEACHER_FAIL);
        }
    }

    @Override
    public ResultUtils deleteById(Integer tid) {
        try {
            teacherMapper.deleteById(tid);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("删除教师异常" + e);
            throw new ValidationException(MessageConstant.DELETE_TEACHER_FAIL);
        }
    }

    @Override
    public ResultUtils login(Teacher teacher) {
        if (StringUtils.isNull(teacher.getTeacherId())) {
            throw new ValidationException(MessageConstant.TEACHER_ID_IS_NOT_EMPTY);
        }
        Teacher currentTeacher = teacherMapper.findById(teacher.getTeacherId());
        if (ObjectUtils.isEmpty(currentTeacher) || !(teacher.getTeacherPwd().equals(currentTeacher.getTeacherPwd()))) {
            throw new ValidationException(MessageConstant.PLEASE_CHECK_PASSWORD);
        }
        return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
    }
}
