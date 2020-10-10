package com.zzx.login.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


public class LogUtils {
    private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

    public static void logHttpRequest(HttpServletRequest request) {
        if (request != null) {
            // 记录下请求内容
            logger.info("URL : {}", request.getRequestURL().toString());
            logger.info("HTTP_METHOD : {}", request.getMethod());
            logger.info("IP : {}", request.getRemoteAddr());
            // 下面循环打印头信息
            Enumeration<String> heads = request.getHeaderNames();
            while (heads.hasMoreElements()) {
                String headName = (String) heads.nextElement();
                logger.info("{}:{}", headName, request.getHeader(headName));
            }
        }
    }
}
