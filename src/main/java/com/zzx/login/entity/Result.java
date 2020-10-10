package com.zzx.login.entity;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String msg;
    private T datas;

    public Result() {
        this(ResultCode.SUCCESS);
    }

    public Result(int code) {
        this(code, null);
    }

    public Result(int code, String msg) {
        this(code, msg, null);
    }

    public Result(int code, String msg, T datas) {
        this.code = code;
        this.msg = msg;
        this.datas = datas;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }
}