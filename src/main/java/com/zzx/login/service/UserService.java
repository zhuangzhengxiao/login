package com.zzx.login.service;

import com.zzx.login.exception.CommonException;

import java.io.UnsupportedEncodingException;

public interface UserService {
//    void register(String account, String password, String phone) throws CommonException;
    void register(String encryptedString) throws CommonException, UnsupportedEncodingException;

    String login(String encryptedString, String ipAddress) throws CommonException, UnsupportedEncodingException;

    String checkToken(String token) throws CommonException;
}
