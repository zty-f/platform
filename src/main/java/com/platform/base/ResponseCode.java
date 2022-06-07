package com.platform.base;

/**
 * @author by Hchier
 * @Date 2022/5/20 10:40
 */
public enum ResponseCode {
    /**
     * 成功
     */
    OK(200, "成功"),
    AUTHENTICATION_FAIL(430, "用户名与密码不匹配"),
    NOT_LOGGED_IN(431, "未登录"),
    DELETE_FAIL(432, "删除失败"),
    REGISTER_FAIL(433, "注册失败"),
    USER_NOT_EXIST(434, "用户不存在"),
    USER_ALREADY_EXIST(434, "用户已存在"),
    TEAM_ALREADY_EXIST(435, "队伍已存在"),
    ADD_FAIL(436, "新增失败"),
    SCHOOL_NOT_EXIST(436, "学校不存在"),
    IDENTITY_ERROR(437, "身份异常"),
    PROJECT_NOT_EXIST(438,"项目不存在"),
    PARAMS_NULL_OR_EMPTY(439,"参数为null或空");

    int code;
    String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
