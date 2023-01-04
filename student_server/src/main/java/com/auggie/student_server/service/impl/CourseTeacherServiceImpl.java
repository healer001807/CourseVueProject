package com.auggie.student_server.service.impl;

import com.auggie.student_server.constant.MessageConstant;
import com.auggie.student_server.entity.Course;
import com.auggie.student_server.entity.CourseTeacher;
import com.auggie.student_server.entity.CourseTeacherInfo;
import com.auggie.student_server.exception.ValidationException;
import com.auggie.student_server.mapper.CourseTeacherMapper;
import com.auggie.student_server.service.CourseTeacherService;
import com.auggie.student_server.utils.ResultCode;
import com.auggie.student_server.utils.ResultUtils;
import com.auggie.student_server.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther:
 * @Date:
 * @Description: CourseTeacherService
 * @Version 1.0.0
 */

@Slf4j
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {
    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Override
    public boolean insertCourseTeacher(Integer cid, Integer tid, String term) {
        return courseTeacherMapper.insertCourseTeacher(cid, tid, term);
    }

    @Override
    public List<Course> findMyCourse(Integer tid, String term) {
        return courseTeacherMapper.findMyCourse(tid, term);
    }

    @Override
    public ResultUtils findCourseTeacherInfo(Map<String, String> map) {
        try {
            Map<String, Object> queryMap = new HashMap<>();
            if (map.containsKey("teacherId") && StringUtils.isNotEmpty(map.get("teacherId"))) {
                queryMap.put("teacherId", Integer.parseInt(map.get("teacherId")));
            }
            if (map.containsKey("courseId") && StringUtils.isNotEmpty(map.get("courseId"))) {
                queryMap.put("courseId", Integer.parseInt(map.get("courseId")));

            }
            if (map.containsKey("teacherName") && StringUtils.isNotEmpty(map.get("teacherName"))) {
                queryMap.put("teacherName", map.get("teacherName"));
            }
            if (map.containsKey("courseName") && StringUtils.isNotEmpty(map.get("courseName"))) {
                queryMap.put("courseName", map.get("courseName"));
            }
            if (map.containsKey("tFuzzy")) {

                queryMap.put("tFuzzy", (map.get("tFuzzy").equals("true")) ? 1 : 0);
            }
            if (map.containsKey("cFuzzy")) {
                queryMap.put("cFuzzy", (map.get("cFuzzy").equals("true")) ? 1 : 0);
            }
            Integer pageNum = 0;
            if (StringUtils.isNotEmpty(map.get("pageNum"))) {
                pageNum = Integer.valueOf(map.get("pageNum"));
            }
            Integer pageSize = 10;
            if (StringUtils.isNotEmpty(map.get("pageSize"))) {
                pageSize = Integer.valueOf(map.get("pageSize"));
            }
            PageHelper.startPage(pageNum, pageSize);
            List<CourseTeacherInfo> courseTeacherInfo = courseTeacherMapper.findCourseTeacherInfo(queryMap);
            PageInfo pageInfo = new PageInfo(courseTeacherInfo);

            log.info("查询开课管理", queryMap);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), MessageConstant.QUERY_COURSE_INFO_SUCCESS,
                    pageInfo);
        } catch (NumberFormatException e) {
            log.error("根据教师id查询课程失败", e);
            throw new ValidationException(MessageConstant.QUERY_COURSE_INFO_ERROR);
        }
    }

    @Override
    public List<CourseTeacher> findBySearch(Integer cid, Integer tid, String term) {
        return courseTeacherMapper.findBySearch(cid, tid, term);
    }

    @Override
    public List<CourseTeacher> findBySearch(Map<String, String> map) {
        Integer cid = null;
        Integer tid = null;
        String term = null;

        if (map.containsKey("term")) {
            term = map.get("term");
        }

        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            } catch (Exception e) {
            }
        }

        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            } catch (Exception e) {
            }
        }
        System.out.println("开课表查询：" + map);
        return courseTeacherMapper.findBySearch(cid, tid, term);
    }

    @Override
    public ResultUtils deleteById(CourseTeacher courseTeacher) {
        try {
            if (ObjectUtils.isEmpty(courseTeacher)) {
                throw new ValidationException(MessageConstant.DELETE_COURSE_ERROR);
            }
            Integer courseTeacherId = courseTeacher.getCourseTeacherId();
            if (StringUtils.isNull(courseTeacherId)) {
                throw new ValidationException(MessageConstant.DELETE_COURSE_INFO_ERROR);
            }
            courseTeacherMapper.deleteById(courseTeacher);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (ValidationException e) {
            log.error("开设课程删除失败" + e);
            throw new ValidationException(MessageConstant.DELETE_COURSE_INFO_ERROR);
        }
    }

}
