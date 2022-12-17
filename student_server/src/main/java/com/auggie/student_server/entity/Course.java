package com.auggie.student_server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @Auther: auggie
 * @Date: 2022/2/9 13:29
 * @Description: Course
 * @Version 1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("Course")
@ApiModel(value = "课程实体")
public class Course {
    @ApiModelProperty(value = "课程编号")
    private Integer courseId;
    @ApiModelProperty("课程名")
    private String courseName;
    @ApiModelProperty("课时")
    private Integer courseCredit;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
