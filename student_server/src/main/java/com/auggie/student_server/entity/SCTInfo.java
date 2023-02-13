package com.auggie.student_server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @Auther:
 * @Date: 2022/2/11 11:14
 * @Description: SCTInfo
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("SCTInfo")
@ApiModel("学生成绩实体")
public class SCTInfo {
    @ApiModelProperty(value = "学号")
    private Integer studentId;
    @ApiModelProperty(value = "教师编号")
    private Integer teacherId;
    @ApiModelProperty(value = "课程编号")
    private Integer courseId;
    @ApiModelProperty(value = "学生姓名")
    private String studentName;
    @ApiModelProperty(value = "教师名")
    private String teacherName;
    @ApiModelProperty(value = "课程名")
    private String courseName;
    @ApiModelProperty(value = "成绩")
    private Float grade;
    @ApiModelProperty(value = "学期")
    private String term;
}
