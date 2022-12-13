package com.auggie.student_server.utils;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * @program: CourseVueProject
 * @description: 分页
 * @author: kangwei
 * @create: 2022-12-13 19:53
 **/
@Data
public class ResponsePages<T> {

    private Integer pageNum;
    private Integer pageSize;
    private List<T> dataList;
    private Long totals;



}
