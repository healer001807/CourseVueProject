package com.auggie.student_server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @Auther:
 * @Date: 2022/2/10 19:57
 * @Description: StudentCourseTeacher
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("StudentCourseTeacher")
@ApiModel(value = "学生成绩实体")
public class StudentCourseTeacher {
    @ApiModelProperty(value = "成绩id")
    private Integer sctId;
    @ApiModelProperty(value = "学号")
    private Integer studentId;
    @ApiModelProperty(value = "课程编号")
    private Integer courseId;
    @ApiModelProperty(value = "教师编号")
    private Integer teacherId;
    @ApiModelProperty(value = "成绩")
    private Float grade;
    @ApiModelProperty(value = "学期")
    private String term;
}
