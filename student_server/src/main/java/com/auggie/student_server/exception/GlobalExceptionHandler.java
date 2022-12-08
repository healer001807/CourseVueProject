package com.auggie.student_server.exception;

import com.auggie.student_server.utils.ResultCode;
import com.auggie.student_server.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: CourseVueProject
 * @description: 全局异常
 * @author: kangwei
 * @create: 2022-12-08 22:50
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 异常场景
     *
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    public ResultUtils error(ValidationException exception) {
        logger.info("异常==>{}", exception.getErrorMsg());
        return ResultUtils.error(ResultCode.ERROR.getCode(), exception.getErrorMsg());
    }
}
