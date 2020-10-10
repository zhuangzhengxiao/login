package com.zzx.login.constant;

public interface Constant {

    interface LOGIN {
        int FAILURE = 0; // 账号或密码错误
        int SUCCESS = 1;
    }

    interface REDIS_KEY {
        String TOKEN = ":token";
    }
}
