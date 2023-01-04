package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.entity.CourseTeacher;
import com.auggie.student_server.entity.CourseTeacherInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther: auggie
 * @Date: 2022/2/10 16:43
 * @Description: CourseTeacherMapper
 * @Version 1.0.0
 */
@Repository
@Mapper
public interface CourseTeacherMapper {

    @Insert("INSERT INTO studentms.ct (cid, tid, term) VALUES (#{cid}, #{tid}, #{term})")
    boolean insertCourseTeacher(@Param("cid") Integer cid,
                                @Param("tid") Integer tid,
                                @Param("term") String term);

    List<CourseTeacher> findBySearch(@Param("cid") Integer cid,
                                     @Param("tid") Integer tid,
                                     @Param("term") String term);

    List<Course> findMyCourse(@Param("tid") Integer tid,
                              @Param("term") String term);

    /**
     * 开课管理
     *
     * @param queryMap
     * @return
     */
    List<CourseTeacherInfo> findCourseTeacherInfo(Map<String, Object> queryMap);

//    @Delete("DELETE FROM studentms.t_course_teacher WHERE ctid = #{c.courseTeacherId}")
    @Update("update  studentms.t_course_teacher set update_time= now() , delete_status =0" +
            " where  ctid = #{c.courseTeacherId}")
    boolean deleteById(@Param("c") CourseTeacher courseTeacher);
}
