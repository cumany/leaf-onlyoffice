package com.ideayp.leaf.common.resp;

import com.ideayp.leaf.common.enums.HttpCodeEnum;

/**
 * <p>Description: 返回工具类  </p>
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2017</p>
 *
 * date 2018/1/4
 * @author yangpeng
 * @version 1.0
 * @since 1.8
 */
@SuppressWarnings("ALL")
public class ApiUtil {

    // ===========================自定义 start============================================

    /**
     * 请求成功
     *
     * @return 响应结果
     */
    public static ApiResult success() {
        return success(null);
    }

    /**
     * 请求成功
     *
     * @param msg 消息
     * @return 响应结果
     */
    public static ApiResult successMsg(String msg) {
        return success(msg, null);
    }

    /**
     * 成功请求
     *
     * @param data 数据
     * @return 结果结果
     */
    public static <T> ApiResult<T> success(T data) {
        return success(HttpCodeEnum.OK.getMsg(), data);
    }

    /**
     * 成功请求
     *
     * @param message 消息
     * @return 返回的数据
     */
    public static <T> ApiResult<T> success(String message, T data) {
        return build(HttpCodeEnum.OK.getCode(), message, data);
    }

    /**
     * 操作失败
     *
     * @return 结果
     */
    public static ApiResult<String> fail() {
        return build(HttpCodeEnum.FAIL);
    }

    /**
     * 操作失败
     *
     * @param msg 返回的信息
     * @return 返回的结果
     */
    public static ApiResult<String> fail(String msg) {
        return build(HttpCodeEnum.FAIL.getCode(), msg, null);
    }

    /**
     * 服务器错误
     *
     * @return 结果
     */
    public static ApiResult error() {
        return build(HttpCodeEnum.INTERNAL_SERVER_ERROR);
    }


    /**
     * 参数错误
     *
     * @return 结果
     */
    public static ApiResult paramError() {
        return build(HttpCodeEnum.UNPROCESABLE_ENTITY);
    }


    /**
     * 没有权限
     *
     * @return 结果
     */
    public static ApiResult unAuthorized() {
        return build(HttpCodeEnum.UNAUTHORIZED);
    }


    /**
     * 禁止访问
     *
     * @return 结果
     */
    public static ApiResult forbidden() {
        return build(HttpCodeEnum.FORBIDDEN);
    }


    /**
     * 资源不存在
     *
     * @return 结果
     */
    public static ApiResult notFound() {
        return notFound(null);
    }


    /**
     * 资源不存在
     *
     * @param data 数据
     * @return 结果
     */
    public static <T> ApiResult<T> notFound(T data) {
        return build(HttpCodeEnum.NOT_FOUND, data);
    }


    /**
     * 请求的格式不正确
     *
     * @return 结果
     */
    public static ApiResult notAcceptable() {
        return build(HttpCodeEnum.NOT_ACCEPTABLE);
    }


    /**
     * 数据已经被删除
     *
     * @return 结果
     */
    public static ApiResult gone() {
        return gone(null);
    }


    /**
     * 数据已经被删除
     *
     * @param data 数据
     * @return 结果
     */
    public static <T> ApiResult<T> gone(T data) {
        return build(HttpCodeEnum.GONE, data);
    }


    /**
     * 实体参数校验错误
     *
     * @return 结果
     */
    public static ApiResult unprocesableEntity() {
        return build(HttpCodeEnum.UNPROCESABLE_ENTITY);
    }


    /**
     * 未知错误
     *
     * @return 结果
     */
    public static ApiResult unKnowError() {
        return unKnowError(null);
    }

    /**
     * 未知错误
     *
     * @param data 数据
     * @return 结果
     */
    public static <T> ApiResult<T> unKnowError(T data) {
        return build(HttpCodeEnum.INTERNAL_SERVER_ERROR, data);
    }

    // ===========================自定义 end============================================

    /**
     * 自定义只返回编码和消息
     *
     * @param httpCodeEnum 状态
     * @return 结果
     */
    public static ApiResult custom(HttpCodeEnum httpCodeEnum) {
        return build(httpCodeEnum, null);
    }

    /**
     * 自定义消息结果
     *
     * @param code    编码
     * @param msg 消息
     * @return 结果
     */
    public static ApiResult custom(int code, String msg) {
        return build(code, msg, null);
    }

    /**
     * 自定义结果
     *
     * @param httpCodeEnum 状态
     * @param data         数量
     * @param <T>          类型
     * @return 结果
     */
    public static <T> ApiResult<T> custom(HttpCodeEnum httpCodeEnum, T data) {
        return build(httpCodeEnum, data);
    }

    /**
     * 自定义返回
     *
     * @param code    编码
     * @param msg 消息
     * @param data    数量
     * @return 结果
     */
    public static <T> ApiResult<T> custom(int code, String msg, T data) {
        return build(code, msg, data);
    }


    /**
     * 创建
     *
     * @param httpCodeEnum 状态码
     * @return 结果
     */
    private static <T> ApiResult<T> build(HttpCodeEnum httpCodeEnum) {
        return build(httpCodeEnum.getCode(), httpCodeEnum.getMsg(), null);
    }

    /**
     * 创建
     *
     * @param httpCodeEnum 状态码
     * @param data         数据
     * @param <T> 自定义类型
     * @return 结果
     */
    private static <T> ApiResult<T> build(HttpCodeEnum httpCodeEnum, T data) {
        return build(httpCodeEnum.getCode(), httpCodeEnum.getMsg(), data);
    }

    /**
     * 基本的创建
     *
     * @param code    编码
     * @param msg 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return 结果
     */
    private static <T> ApiResult<T> build(int code, String msg, T data) {
        return new ApiResult<>(code, msg, data);
    }

}
