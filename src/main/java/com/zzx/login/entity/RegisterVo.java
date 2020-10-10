package com.zzx.login.entity;

import lombok.Data;


@Data
public class RegisterVo {
    private String account;
    private String password;
    private String extensionParam; // 扩展参数（估计也会是json字符串 ）
}
