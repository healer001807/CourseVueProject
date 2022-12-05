package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

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
public class Student {
    /**
     * 学生id
     */

    private Integer studentId;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 学生密码
     */
    private String studentPwd;
}
