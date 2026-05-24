package com.careernavigator.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAILURE(500, "操作失败"),

    // 认证相关 1xxx
    UNAUTHORIZED(1001, "未登录或Token已过期"),
    FORBIDDEN(1002, "无权限访问"),
    TOKEN_INVALID(1003, "Token无效"),
    TOKEN_EXPIRED(1004, "Token已过期"),
    USERNAME_EXISTS(1005, "用户名已存在"),
    EMAIL_EXISTS(1006, "邮箱已被注册"),
    USER_NOT_FOUND(1007, "用户不存在"),
    PASSWORD_ERROR(1008, "密码错误"),

    // 参数相关 2xxx
    PARAM_ERROR(2001, "参数错误"),
    PARAM_MISSING(2002, "缺少必要参数"),
    PARAM_TYPE_ERROR(2003, "参数类型错误"),

    // 业务相关 3xxx
    DATA_NOT_FOUND(3001, "数据不存在"),
    DATA_ALREADY_EXISTS(3002, "数据已存在"),
    OPERATION_FAILED(3003, "操作失败"),

    // AI相关 4xxx
    AI_PROVIDER_ERROR(4001, "AI服务调用失败"),
    AI_QUOTA_EXCEEDED(4002, "AI调用次数已用完"),
    AI_PROVIDER_NOT_FOUND(4003, "AI供应商不存在"),
    AI_RESPONSE_ERROR(4004, "AI响应解析失败"),

    // 系统相关 5xxx
    SYSTEM_ERROR(5001, "系统内部错误"),
    SERVICE_UNAVAILABLE(5002, "服务暂不可用");

    private final int code;
    private final String message;
}
