package com.zzx.login.exception;


import com.alibaba.fastjson.JSONException;
import com.zzx.login.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;


/**
 * 全局的的异常拦截器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public Result commonException(CommonException e) {
        Result result = new Result<>();
        result.setCode(0);
        result.setMsg(e.getMsg());
        return result;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result nullPointerException(NullPointerException e) {
        Result result = new Result<>();
        result.setCode(0);
        result.setMsg("空指针异常!");
        return result;
    }

    /**
     * base64解码异常 或者 json解析异常
     */
    @ExceptionHandler({UnsupportedEncodingException.class ,JSONException.class})
    @ResponseBody
    public Result parseException(UnsupportedEncodingException ue,JSONException je) {
        Result result = new Result<>();
        result.setCode(0);
        result.setMsg("解析错误!");
        return result;
    }
}
