package com.auggie.student_server.utils;

import cn.hutool.core.util.ObjectUtil;
import com.auggie.student_server.service.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Locale;

/**
 * @program: CourseVueProject
 * @description: 统一拦截所有返回
 * @author: kangwei
 * @create: 2023-05-13 20:41
 **/
@RestControllerAdvice
public class GlobalResBody implements ResponseBodyAdvice {

    @Autowired
    private I18nService i18nService;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        System.out.println(request);
        HttpHeaders headers = request.getHeaders();
        List<Locale.LanguageRange> acceptLanguage = headers.getAcceptLanguage();
        // 非空校验
        if (ObjectUtil.isNotEmpty(body)) {
            if (body instanceof ResultUtils) {
                // 强转
                ResultUtils dataBody = (ResultUtils) body;
                if (ObjectUtil.isNotEmpty(dataBody.getReturnMsg())) {
                    // 国际化配置，只处理code 和msg
                    dataBody.setReturnMsg(i18nService.getMsg(dataBody.getReturnMsg()));
                }
                return body;
            }
        }

        ResultUtils dataBody = ResultUtils.success(body);
        if (ObjectUtil.isNotEmpty(dataBody.getReturnMsg())) {
            dataBody.setReturnMsg(i18nService.getMsg(dataBody.getReturnMsg()));
        }
        return dataBody;
    }
}
