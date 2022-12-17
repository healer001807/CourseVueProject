package com.auggie.student_server.service.impl;

import com.auggie.student_server.constant.MessageConstant;
import com.auggie.student_server.entity.Course;
import com.auggie.student_server.exception.ValidationException;
import com.auggie.student_server.mapper.CourseMapper;
import com.auggie.student_server.service.CourseService;
import com.auggie.student_server.utils.ResultCode;
import com.auggie.student_server.utils.ResultUtils;
import com.auggie.student_server.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: auggie
 * @Date: 2022/2/9 13:46
 * @Description: CourseService
 * @Version 1.0.0
 */
@Slf4j
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public ResultUtils findBySearch(Map<String, String> map) {
        try {
            Integer lowBound = null;
            Integer highBound = null;
            Integer fuzzy = 0;
            Course course = new Course();
            if (map.containsKey("courseId") && StringUtils.isNotEmpty(map.get("courseId"))) {
                course.setCourseId(Integer.parseInt(map.get("courseId")));
            }

            if (map.containsKey("lowBound") && StringUtils.isNotEmpty(map.get("lowBound"))) {
                lowBound = Integer.parseInt(map.get("lowBound"));
            }
            if (map.containsKey("highBound") && StringUtils.isNotEmpty(map.get("highBound"))) {
                highBound = Integer.valueOf(map.get("highBound"));
            }
            if (map.containsKey("courseName") && StringUtils.isNotEmpty(map.get("courseName"))) {
                course.setCourseName(map.get("courseName"));
            }
            if (map.containsKey("fuzzy")) {
                fuzzy = ("true".equals(map.get("fuzzy"))) ? 1 : 0;
            }
            log.info(course + " " + fuzzy + " " + lowBound + " " + highBound);
            List<Course> courses = courseMapper.findBySearch(course, fuzzy, lowBound, highBound);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), courses);
        } catch (NumberFormatException e) {
            log.error("课程查询失败" + e);
            throw new ValidationException(MessageConstant.QUERY_COURSE_ERROR);
        }
    }


    @Override
    public ResultUtils findById(Integer courseId) {
        try {
            HashMap<String, String> map = new HashMap<>();
            if (StringUtils.isNull((courseId))) {
                throw new ValidationException(MessageConstant.COURSE_ID_IS_NOT_EMPTY);
            }
            map.put("courseId", String.valueOf(courseId));
            return findBySearch(map);
        } catch (Exception e) {
            log.error("根据id查询课程失败" + e);
            throw new ValidationException(MessageConstant.QUERY_COURSE_ERROR);
        }
    }

    @Override
    public ResultUtils updateById(Course course) {
        try {
            courseMapper.updateById(course);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("更新课程失败" + e);
            throw new ValidationException(MessageConstant.UPDATE_COURSE_ERROR);
        }
    }

    @Override
    public ResultUtils insertCourse(Course course) {
        try {
            courseMapper.insertCourse(course);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("保存课程失败" + e);
            throw new ValidationException(MessageConstant.SAVE_COURSE_ERROR);
        }
    }

    public ResultUtils deleteById(Integer cid) {
        try {
            courseMapper.deleteById(cid);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("保存课程失败" + e);
            throw new ValidationException(MessageConstant.DELETE_COURSE_ERROR);
        }
    }

    @Override
    public ResultUtils findAll(Integer pageNum, Integer pageSize) {
        try {
            if (StringUtils.isNull(pageNum)) {
                pageNum = 1;
            }
            if (StringUtils.isNull(pageSize)) {
                pageSize = 10;
            }
            PageHelper.startPage(pageNum, pageSize);
            List<Course> courses = courseMapper.findAll();
            PageInfo pageInfo = new PageInfo(courses);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), pageInfo);
        } catch (Exception e) {
            log.error("分页查询失败" + e);
            throw new ValidationException(MessageConstant.QUERY_COURSE_ERROR);
        }
    }


}
