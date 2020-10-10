package com.zzx.login.exception;


public class CommonException extends Exception {
    private String msg;
    public CommonException(String msg) {
        super();
        this.msg = msg;
    }
    public String getMsg(){
        return msg;
    }
}
