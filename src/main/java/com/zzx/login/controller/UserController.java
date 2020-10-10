package com.zzx.login.controller;

import com.zzx.login.entity.EncryptedString;
import com.zzx.login.entity.Result;
import com.zzx.login.exception.CommonException;
import com.zzx.login.service.UserService;
import com.zzx.login.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(("/user"))
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("register")
    @CrossOrigin
    public Result register (@RequestBody EncryptedString vo) throws CommonException, UnsupportedEncodingException {
        userService.register(vo.getP());
        return new Result();
    }

    @PostMapping("login")
    @CrossOrigin
    public Result login (@RequestBody EncryptedString vo, HttpServletRequest request) throws CommonException, UnsupportedEncodingException {
        String token = userService.login(vo.getP(), RequestUtils.getIpAddress(request));
        Result result = new Result<>();
        result.setMsg("登录成功");
        result.setDatas(token);
        return result;
    }

    @PostMapping("checkToken")
    @CrossOrigin
    public Result checkToken (HttpServletRequest request) throws CommonException {
        // 校验完成会重新返回一个新的token
        String newToken = userService.checkToken(request.getHeader("token"));
        Result result = new Result();
        result.setDatas(newToken);
        return result;
    }

    @GetMapping("test")
    public String test(){
        return "拦截";
    }
}
