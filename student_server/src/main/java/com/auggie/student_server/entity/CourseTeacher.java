package com.auggie.student_server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @Auther: auggie
 * @Date: 2022/2/10 16:55
 * @Description: CourseTeacher
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("CourseTeacher")
@ApiModel("教师开设课程实体")
public class CourseTeacher {
    @ApiModelProperty(value = "教师开设课程编号")
    private Integer courseTeacherId;
    @ApiModelProperty(value = "课程编号")
    private Integer courseId;
    @ApiModelProperty(value = "教师编号")
    private Integer teacherId;
    @ApiModelProperty(value = "学期")
    private String term;
}
