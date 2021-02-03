package com.ideayp.leaf.common.enums;

/**
 * ----------------------------------------------------------------------------
 * 200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
 * 400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
 * 401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
 * 403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
 * 404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
 * 406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
 * 410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
 * 422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
 * 500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
 * 600 UN_KNOW_ERROR - 未知错误
 * ----------------------------------------------------------------------------
 * <p>Description: http 状态码  </p>
 *
 * @author yangpeng
 */
@SuppressWarnings("ALL")
public enum HttpCodeEnum {
    // 成功
    OK(200, "操作成功"),
    // 客户端请求的语法错误，服务器无法理解
    BAD(400, "参数错误"),
    // 没有权限 请求要求用户的身份认证
    UNAUTHORIZED(401, "对不起，您没有权限，请联系管理员"),
    // 服务器理解请求客户端的请求，但是拒绝执行此请求
    FORBIDDEN(403, "禁止访问"),
    // 找不到资源
    NOT_FOUND(404, "资源不存在"),
    // 方法请求错误
    METHOD_NOT_ALLOWED(405, "方法不被允许"),
    // 请求的格式不正确
    NOT_ACCEPTABLE(406, "请求的格式不正确"),

    GONE(410, "数据被删除"),
    UNSUPPORTED_MEDIA_TYPE(415, "Media Type错误"),
    UNPROCESABLE_ENTITY(422, "参数验证错误"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误，无法完成请求"),

    FAIL(501, "操作失败"),
    BUSINESS(503, "业务异常"),
    BUSY_BUSINESS(504, "业务繁忙,请稍后再试"),
    /**
     * 登陆相关
     */
    // 未登录
    NOT_LOGIN(999, "请登录后再试"),
    NOT_LOGIN_OTHER(1000, "请重新登陆，已经在其他地方的登陆过了"),
    // 登陆过期
    NO_LOGIN_EXPIRE(1001, "登陆过期,请重新登录"),
    NOT_LOGINMODEL(1002, "请输入账号信息"),
    NOT_KNOWN_ACCOUNT(1003, "账号错误"),
    NOT_KNOWN_PASSWORD(1004, "密码错误"),
    FORBID_LOGIN(1005, "禁止登陆,请联系管理员"),
    EXIST_MOBILE(1006, "手机号已存在"),
    EXIST_USERNAME(1007, "用户名已存在"),
    EXIST_EMAIL(1008, "邮箱已存在"),
    MOBILE_ERROR(1009, "不是一个合法的手机号"),
    EMAIL_ERROR(1010, "不是一个合法的电子邮箱"),
    USERNAME_NOT_NULL(1011, "用户名不能为空"),

    /**
     * 验证码相关
     */
    VERCODE_SEND(1100, "验证码发送成功"),
    MOBILE_VERCODE_ERROR(1101, "验证码错误"),
    VERCODE_EXIST(1102, "当前验证码未过期，请稍后重试"),
    VERCODE_EXCEED(1103, "超过当日验证码次数上限"),
    VERCODE_ORDER(1104, "请稍后再试，发送时间间隔短"),

    /* ip相关 */
    NOT_IP(1201, "无法获取ip");

    /**
     * 编码
     */
    private final int code;
    /**
     * 消息
     */
    private final String msg;

    HttpCodeEnum(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
