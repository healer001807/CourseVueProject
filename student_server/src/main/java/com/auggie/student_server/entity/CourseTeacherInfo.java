package com.auggie.student_server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @Auther:
 * @Date: 2022/2/10 18:45
 * @Description: CourseTeacherInfo
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("CourseTeacherInfo")
@ApiModel("开课课程信息")
public class CourseTeacherInfo {
    @ApiModelProperty("开设课程id")
    private Integer courseTeacherId;

    @ApiModelProperty("课程id")
    private Integer courseId;
    @ApiModelProperty("教师id")
    private Integer teacherId;
    @ApiModelProperty("课程名")
    private String courseName;
    @ApiModelProperty("教师名")
    private String teacherName;
    @ApiModelProperty("课程学分")
    private Integer courseCredit;
    @ApiModelProperty("成绩")
    private Float grade;

    @ApiModelProperty("成绩管理编号")
    private Integer scoreId;
    @ApiModelProperty("学生编号")
    private Integer studentId;
    private String term;
    @JsonFormat(locale = "yyyy-MM-dd")
    private Date createTime;
    private String studentName;
}
