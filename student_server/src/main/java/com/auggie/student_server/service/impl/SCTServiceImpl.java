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
    public boolean deleteById(Integer sid, Integer cid, Integer tid, String term) {
        StudentCourseTeacher studentCourseTeacher = new StudentCourseTeacher();
        studentCourseTeacher.setSid(sid);
        studentCourseTeacher.setCid(cid);
        studentCourseTeacher.setTid(tid);
        studentCourseTeacher.setTerm(term);
        return studentCourseTeacherMapper.deleteBySCT(studentCourseTeacher);
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
    public List<SCTInfo> findBySearch(Map<String, String> map) {
        Integer sid = null, cid = null, tid = null;
        String sname = null, cname = null, tname = null, term = null;
        Integer sFuzzy = null, cFuzzy = null, tFuzzy = null;
        Integer lowBound = null, highBound = null;

        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("sid")) {
            try {
                sid = Integer.parseInt(map.get("sid"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("sname")) {
            sname = map.get("sname");
        }
        if (map.containsKey("tname")) {
            tname = map.get("tname");
        }
        if (map.containsKey("cname")) {
            cname = map.get("cname");
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
        if (map.containsKey("lowBound")) {
            try {
                lowBound = Integer.parseInt(map.get("lowBound"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("highBound")) {
            try {
                highBound = Integer.valueOf(map.get("highBound"));
            } catch (Exception e) {
            }
        }

        System.out.println("SCT 查询：" + map);
        return studentCourseTeacherMapper.findBySearch(
                sid, sname, sFuzzy,
                cid, cname, cFuzzy,
                tid, tname, tFuzzy,
                lowBound, highBound, term);
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
}
