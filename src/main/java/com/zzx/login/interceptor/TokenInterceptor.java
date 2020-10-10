package com.zzx.login.interceptor;

import com.zzx.login.exception.CommonException;
import com.zzx.login.service.TokenService;
import com.zzx.login.util.CookieUtil;
import com.zzx.login.util.LogUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token拦截器拦截校验
 */
@Slf4j
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = request.getHeader("Token");
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader("token");
        }
        if (StringUtils.isEmpty(token)) {
            token = CookieUtil.getTokenCookie(request);
        }
        if (StringUtils.isEmpty(token)) {
            LogUtils.logHttpRequest(request);
            throw new CommonException("非法请求,请传入Authorization");
        } else {
            Claims claims = tokenService.parseJWT(token);
            if (claims == null) {
                LogUtils.logHttpRequest(request);
                throw new CommonException("非法请求,token错误或失效");
            } else {
                return true;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//        logger.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
//        logger.info("afterCompletion");
    }
}
