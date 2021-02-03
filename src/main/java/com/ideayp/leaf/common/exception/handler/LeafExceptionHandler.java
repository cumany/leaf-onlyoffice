package com.ideayp.leaf.common.exception.handler;


import com.alibaba.fastjson.JSONException;
import com.ideayp.leaf.common.enums.HttpCodeEnum;
import com.ideayp.leaf.common.exception.*;
import com.ideayp.leaf.common.resp.ApiResult;
import com.ideayp.leaf.common.resp.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.Set;

/**
 * <p>Description:  通用异常处理类 </p>
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2017</p>
 *
 * @author yangpeng
 * @version 1.0
 * @date 2018/1/4
 * @since 1.8
 */
@SuppressWarnings("ALL")
@ControllerAdvice
@ResponseBody
@Slf4j
public class LeafExceptionHandler {


    /**
     * 业务异常
     *
     * @param e 自定义异常
     * @return 结果
     */
    @ExceptionHandler(LeafException.class)
    public ApiResult handleLeafException(LeafException e) {
        log.error(e.getMessage());
        // 服务器错误
        return e.getApiResult();
    }

    /**
     * 绑定异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class})
    public ApiResult bindExceptionHandler(BindException e) {
        log.error(e.getMessage());
        return ApiUtil.custom(HttpCodeEnum.UNPROCESABLE_ENTITY);
    }

    /**
     * 方法验证错误
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiResult handle(MethodArgumentNotValidException exception) {
        String defaultMessage = exception.getBindingResult().getFieldError().getDefaultMessage();
        return ApiUtil.custom(HttpCodeEnum.BAD.getCode(), defaultMessage);
    }

    /**
     * 400 - Bad Request
     *
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResult handleHttpMessageNotReadableException(Exception e) {
        return ApiUtil.custom(HttpCodeEnum.UNPROCESABLE_ENTITY);

    }

    /**
     * 405 - Method Not Allowed
     *
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult handleHttpRequestMethodNotSupportedException(Exception e) {
        return ApiUtil.custom(HttpCodeEnum.METHOD_NOT_ALLOWED);
    }

    /**
     * 415 - Unsupported Media Type
     *
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApiResult handleHttpMediaTypeNotSupportedException(Exception e) {
        return ApiUtil.custom(HttpCodeEnum.UNSUPPORTED_MEDIA_TYPE);

    }

    /**
     * json转换错误
     *
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(JSONException.class)
    public ApiResult jsonConvert(Exception e) {
        log.error(e.getMessage());
        return ApiUtil.notAcceptable();
    }
}
