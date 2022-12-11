package com.auggie.student_server.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @Auther: auggie
 * @Date: 2022/2/8 16:11
 * @Description: Student
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("Student")
@ApiModel("学生实体")
public class Student {

    @ApiModelProperty(value = "学生id")
    private Integer studentId;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "学生密码")
    private String studentPwd;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
