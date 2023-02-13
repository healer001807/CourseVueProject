package com.auggie.student_server.service.impl;

import com.auggie.student_server.constant.MessageConstant;
import com.auggie.student_server.entity.CourseTeacherInfo;
import com.auggie.student_server.entity.SCTInfo;
import com.auggie.student_server.entity.StudentCourseTeacher;
import com.auggie.student_server.exception.ValidationException;
import com.auggie.student_server.mapper.StudentCourseTeacherMapper;
import com.auggie.student_server.service.SCTService;
import com.auggie.student_server.utils.ResultCode;
import com.auggie.student_server.utils.ResultUtils;
import com.auggie.student_server.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: auggie
 * @Date: 2022/2/10 20:10
 * @Description: SCTService
 * @Version 1.0.0
 */

@Service
public class SCTServiceImpl implements SCTService {
    private static final Logger logger = LoggerFactory.getLogger(SCTServiceImpl.class);
    @Autowired
    private StudentCourseTeacherMapper studentCourseTeacherMapper;

    @Override
    public List<CourseTeacherInfo> findBySid(Integer sid, String term) {
        return studentCourseTeacherMapper.findByStudentId(sid, term);
    }

    @Override
    public ResultUtils findAllTerm() {
        List<String> allTerm = studentCourseTeacherMapper.findAllTerm();
        return ResultUtils.success(ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMsg(), allTerm);
    }

    @Override
    public boolean isSCTExist(StudentCourseTeacher studentCourseTeacher) {
        return studentCourseTeacherMapper.findBySCT(studentCourseTeacher).size() != 0;
    }

    @Override
    public boolean save(StudentCourseTeacher studentCourseTeacher) {
        return studentCourseTeacherMapper.insert(studentCourseTeacher);
    }

    @Override
    public boolean deleteBySCT(StudentCourseTeacher studentCourseTeacher) {
        return studentCourseTeacherMapper.deleteBySCT(studentCourseTeacher);
    }

    @Override
    public ResultUtils deleteById(Integer studentId, Integer courseId, Integer teacherId, String term) {
        try {
            StudentCourseTeacher studentCourseTeacher = new StudentCourseTeacher();
            studentCourseTeacher.setStudentId(studentId);
            studentCourseTeacher.setCourseId(courseId);
            studentCourseTeacher.setTeacherId(teacherId);
            studentCourseTeacher.setTerm(term);
            studentCourseTeacherMapper.deleteBySCT(studentCourseTeacher);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (Exception e) {
            throw new ValidationException(MessageConstant.DELETE_SCORE_ERROR);
        }
    }

    @Override
    public SCTInfo findByIdWithTerm(Integer sid, Integer cid, Integer tid, String term) {
        return studentCourseTeacherMapper.findBySearch(
                sid, null, 0,
                cid, null, 0,
                tid, null, 0,
                null, null, term).get(0);
    }

    @Override
    public ResultUtils updateById(Map<String, String> params) {

        try {
            if (StringUtils.isEmpty(params.get("scoreId"))) {
                throw new ValidationException(MessageConstant.UPDATE_SCORE_ERROR);
            }
            if (StringUtils.isEmpty(params.get("term"))) {
                throw new ValidationException(MessageConstant.UPDATE_SCORE_ERROR);
            }
            if (StringUtils.isEmpty(params.get("grade"))) {
                throw new ValidationException(MessageConstant.UPDATE_SCORE_ERROR);
            }
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("scoreId", Integer.valueOf(params.get("scoreId")));
            updateMap.put("term", params.get("term"));
            updateMap.put("grade", params.get("grade"));
            studentCourseTeacherMapper.updateById(updateMap);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("修改学生成绩异常" + e);
            throw new ValidationException(MessageConstant.UPDATE_SCORE_ERROR);
        }
    }

    @Override
    public ResultUtils findBySearch(Map<String, String> map) {
        try {
            Integer studentId = null, courseId = null, teacherId = null;
            String studentName = null, courseName = null, teacherName = null, term = null;
            Integer sFuzzy = null, cFuzzy = null, tFuzzy = null;
            Integer lowBound = null, highBound = null;

            if (map.containsKey("courseId") && StringUtils.isNotEmpty(map.get("courseId"))) {
                try {
                    courseId = Integer.parseInt(map.get("courseId"));
                } catch (Exception e) {
                }
            }
            if (map.containsKey("studentId") && StringUtils.isNotEmpty(map.get("studentId"))) {
                try {
                    studentId = Integer.parseInt(map.get("studentId"));
                } catch (Exception e) {
                }
            }
            if (map.containsKey("teacherId") && StringUtils.isNotEmpty(map.get("teacherId"))) {
                teacherId = Integer.parseInt(map.get("teacherId"));
            }
            if (map.containsKey("studentName") && StringUtils.isNotEmpty(map.get("studentName"))) {
                studentName = map.get("studentName");
            }
            if (map.containsKey("teacherName") && StringUtils.isNotEmpty(map.get("teacherName"))) {
                teacherName = map.get("teacherName");
            }
            if (map.containsKey("courseName") && StringUtils.isNotEmpty(map.get("courseName"))) {
                courseName = map.get("courseName");
            }
            if (map.containsKey("term")) {
                term = map.get("term");
            }
            if (map.containsKey("sFuzzy")) {
                sFuzzy = map.get("sFuzzy").equals("true") ? 1 : 0;
            }
            if (map.containsKey("tFuzzy")) {
                tFuzzy = map.get("tFuzzy").equals("true") ? 1 : 0;
            }
            if (map.containsKey("cFuzzy")) {
                cFuzzy = map.get("cFuzzy").equals("true") ? 1 : 0;
            }
            if (map.containsKey("lowBound") && StringUtils.isNotEmpty(map.get("lowBound"))) {
                lowBound = Integer.parseInt(map.get("lowBound"));
            }
            if (map.containsKey("highBound") && StringUtils.isNotEmpty(map.get("highBound"))) {
                highBound = Integer.valueOf(map.get("highBound"));
            }

            logger.info("SCT 查询：" + map);
            List<SCTInfo> sctInfos = studentCourseTeacherMapper.findBySearch(
                    studentId, studentName, sFuzzy,
                    courseId, courseName, cFuzzy,
                    teacherId, teacherName, tFuzzy,
                    lowBound, highBound, term);
            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), sctInfos);
        } catch (NumberFormatException e) {
            logger.error("查询成绩异常" + e);
            throw new ValidationException(MessageConstant.QUERY_SCORE_ERROR);
        }
    }

    @Override
    public ResultUtils findAll(Map<String, Object> params) {
        try {
            Integer pageNum = 1;
            Integer pageSize = 10;
            if (StringUtils.isNotNull((Integer) params.get("pageNum"))) {
                pageNum = (Integer) params.get("pageNum");
            }
            if (StringUtils.isNotNull((Integer) params.get("pageSize"))) {
                pageSize = (Integer) params.get("pageSize");
            }
            PageHelper.startPage(pageNum, pageSize);
            List<CourseTeacherInfo> infoList = studentCourseTeacherMapper.findAll(params);

            PageInfo pageInfo = new PageInfo(infoList);

            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), pageInfo);
        } catch (NumberFormatException e) {
            logger.error("查询成绩异常" + e);
            throw new ValidationException(MessageConstant.QUERY_SCORE_ERROR);
        }
    }

    @Override
    public ResultUtils findGradeById(CourseTeacherInfo courseTeacherInfo) {
        return null;
    }

    @Override
    public ResultUtils findCourserInfoById(Integer studentId, String term) {
        try {
            if (StringUtils.isEmpty(term) || StringUtils.isNull(studentId)) {
                throw new ValidationException(MessageConstant.QUERY_COURSE_ERROR);
            }
            List<CourseTeacherInfo> courseTeacherInfos = studentCourseTeacherMapper.findByStudentId(studentId, term);

            return ResultUtils.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), courseTeacherInfos);
        } catch (ValidationException e) {
            logger.error("课程查询异常" + e);
            throw new ValidationException(MessageConstant.QUERY_COURSE_ERROR);
        }
    }
}
