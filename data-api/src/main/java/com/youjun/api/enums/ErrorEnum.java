package com.youjun.api.enums;

import com.youjun.common.api.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorEnum
 * @description error类型枚举类
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum implements IErrorCode {
    // 系统错误
    UNKNOWN(500,"系统内部错误，请联系管理员"),
    PATH_NOT_FOUND(404,"路径不存在，请检查路径"),
    NO_AUTH(403,"没有权限，请联系管理员"),
    DUPLICATE_KEY(501,"数据库中已存在该记录"),
    TOKEN_GENERATOR_ERROR(502,"token生成失败"),
    NO_UUID(503,"uuid为空"),
    SQL_ILLEGAL(504,"sql非法"),

    //用户权限错误
    INVALID_TOKEN(1001,"token不合法"),
    INVALID_USERNAME(1002,"用户名已存在"),
    INVALID_USERNAME_NULL(1003,"用户名不存在"),
    INVALID_PHONE_NULL(1004,"用户不存在，请点击注册"),
    INVALID_CUST_CORP_INFO(1005,"账户认证中"),
    INVALID_PHONE_INFO_EXITS(1006,"手机号已注册"),
    INVALID_PASSWORD(1007,"用户名或密码错误"),
    USERINFO_FREEZE(1008,"账户已被冻结"),
    USERINFO_NOT_PASS(1008,"账号未认证"),
    USERINFO_NO_PASS(1009,"账号认证未通过"),

    //登录模块错误
    LOGIN_FAIL(10001,"登录失败"),
    CAPTCHA_WRONG(10002,"验证码错误"),
    USERNAME_OR_PASSWORD_WRONG(10003,"用户名或密码错误"),
    PHONE_USER_NULL(10004,"手机号不存在"),
    LOGIN_TIME_OUT(10005,"登录超时"),
    PHONE_NOT_REGISTER(10006,"该手机号未注册，请先注册"),
    CARTNO_EXIST(10007, "身份证号已存在"),

    // 请求错误
    REQUEST_ARGUMENTS_EMPTY(30001,"请求参数不能为空"),
    REQUEST_ARGUMENTS_EXITES(30002,"请求参数已存在"),
    REQUEST_ARGUMENTS_ERROR(30011,"请求参数错误"),
    REQUEST_FILE_EMPTY(30003,"文件为空"),
    REQUEST_FILE_ERROR(30004,"上传文件失败"),
    REQUEST_LOGIN_CODE_EMPTY(30005,"短信验证码不能为空"),
    REQUEST_LOGIN_PHONE_EMPTY(30006,"手机号不能为空"),
    REQUEST_LOGIN_CODE_ERROR(30007,"短信验证码错误"),
    REQUEST_SEND_CODE_ERROR(30008,"短信发送失败"),
    REQUEST_SEND_PHONE_EMPTY(30009,"手机号不能为空"),
    REQUEST_FILE_NOT_FOUND(30010,"文件未找到"),
    REQUEST_PC_LOGIN_CODE_ERROR(30012,"验证码错误"),
    REQUEST_DRIVER_EXITS(30013,"司机信息已添加"),
    REQUEST_PHONE_EXITS(30014,"该手机号已存在"),
    REQUEST_PRODUCT_EXITS(30015,"该产品名称已存在"),

    // 数据库错误
    INSERT_DB_ERROR(40001, "数据库数据插入失败"),

    //APP热更新
    UPGRADE_VERSION_ERROR(150001, "版本错误"),
    APP_LATEST_VERSION_NOT_EXISTS(150001, "App最新版本不存在"),

    //配置信息
    SYSCONFIG_EXISTS(160001, "配置信息已存在");

    private long code;
    private String message;
}
