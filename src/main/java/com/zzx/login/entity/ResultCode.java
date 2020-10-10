package com.zzx.login.entity;

public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(1, "请求成功"),

    FAILED(0, "请求失败");


    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}