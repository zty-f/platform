package com.platform.base;

/**
 * 常量
 *
 *
 * @author by Hchier
 * @Date 2022/5/25 20:33
 */
public enum Const {
    /**
     *
     */
    ROLE_ADMIN("admin"),
    ROLE_STUDENT("student"),
    ROLE_TEACHER("teacher");

    String message;

    Const(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
