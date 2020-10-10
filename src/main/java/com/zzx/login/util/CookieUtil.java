package com.zzx.login.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtil {
    private static final String TOKEN = "token";

    public static Cookie[] getCookie(HttpServletRequest request) {
        return request.getCookies();
    }

    public static void setTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(TOKEN, token);
        cookie.setPath("/ydj/api");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    public static String getTokenCookie(HttpServletRequest request) {
        String res = null;
        Cookie[] cookies = getCookie(request);
        if (CollectionUtil.isEmpty(cookies))
            return null;
        for (Cookie cookie : cookies) {
            if (TOKEN.equals(cookie.getName()))
                res = cookie.getValue();
        }
        return res;
    }
}
