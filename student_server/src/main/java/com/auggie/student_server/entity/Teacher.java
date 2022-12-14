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
 * @Date: 2022/2/9 10:50
 * @Description: Teacher
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("Teacher")
@ApiModel("老师信息实体")
public class Teacher {
    @ApiModelProperty("编号")
    private Integer teacherId;
    @ApiModelProperty("教师姓名")
    private String teacherName;
    @ApiModelProperty("密码")
    private String teacherPwd;
    private String studentPwd;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty("操作模式 0-已删除 1-未删除")
    private Date deleteStatus;
}
