package com.platform.base;

/**
 * @author by Hchier
 * @Date 2022/5/20 10:39
 */

public class RestResponse<T> {
    private int code;
    private String message;
    private T responseBody;

    public RestResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResponse(int code, String message, T responseBody) {
        this.code = code;
        this.message = message;
        this.responseBody = responseBody;
    }


    public static RestResponse fail(int code, String message) {
        return RestResponse.fail(code, message, null);
    }

    public static <F> RestResponse<F> fail(int code, String message, F responseBody) {
        return new RestResponse<>(code, message, responseBody);
    }

    public static RestResponse ok() {
        return new RestResponse<>(ResponseCode.OK.code, ResponseCode.OK.message);
    }

    public static <F> RestResponse<F> ok(F responseBody) {
        return new RestResponse<F>(ResponseCode.OK.code, ResponseCode.OK.message, responseBody);
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
