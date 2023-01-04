package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.CourseTeacherInfo;
import com.auggie.student_server.entity.SCTInfo;
import com.auggie.student_server.entity.StudentCourseTeacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther: auggie
 * @Date: 2022/2/10 19:58
 * @Description: StudentCourseTeacherMapper
 * @Version 1.0.0
 */

@Repository
@Mapper
public interface StudentCourseTeacherMapper {

    public List<CourseTeacherInfo> findByStudentId(@Param("sid") Integer sid,
                                                   @Param("term") String term);

    public List<SCTInfo> findBySearch(@Param("sid") Integer sid,
                                      @Param("sname") String sname,
                                      @Param("sFuzzy") Integer sFuzzy,
                                      @Param("cid") Integer cid,
                                      @Param("cname") String cname,
                                      @Param("cFuzzy") Integer cFuzzy,
                                      @Param("tid") Integer tid,
                                      @Param("tname") String tname,
                                      @Param("tFuzzy") Integer tFuzzy,
                                      @Param("lowBound") Integer lowBound,
                                      @Param("highBound") Integer highBound,
                                      @Param("term") String term);

    @Select("SELECT DISTINCT sct.term FROM studentms.t_score_course_teacher sct")
    public List<String> findAllTerm();

    @Select("SELECT * FROM studentms.t_score_course_teacher WHERE sid = #{sct.sid} AND cid = #{sct.cid} AND tid = #{sct.tid} AND term = #{sct.term}")
    public List<StudentCourseTeacher> findBySCT(@Param("sct") StudentCourseTeacher studentCourseTeacher);

    @Insert("INSERT INTO studentms.t_score_course_teacher (sid, cid, tid, term) VALUES (#{s.sid}, #{s.cid}, #{s.tid}, #{s.term})")
    public boolean insert(@Param("s") StudentCourseTeacher studentCourseTeacher);

    /**
     * 修改成绩
     *
     * @param params
     * @return
     */
    @Update("UPDATE studentms.t_score_course_teacher sct SET sct.grade = #{grade}, sct.update_time =now()" +
            " WHERE sct.sctid =#{scoreId} and sct.term = #{term}")
    boolean updateById(Map<String, Object> params);

    @Delete("DELETE FROM studentms.t_score_course_teacher WHERE sid = #{sct.sid} AND tid = #{sct.tid} AND cid = #{sct.cid}")
    public boolean deleteBySCT(@Param("sct") StudentCourseTeacher sct);

    /**
     * 查询成绩
     *
     * @param params
     * @return
     */

    List<CourseTeacherInfo> findAll(Map<String, Object> params);
}
