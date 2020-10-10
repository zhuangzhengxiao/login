package com.zzx.login.util;
import com.zzx.login.exception.CommonException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Component
public class Base64Util {
    final Base64.Decoder decoder = Base64.getDecoder();

    /**
     *
     * @param base64String 前端传来的编码后的字符串
     * @return
     * @throws UnsupportedEncodingException
     * @throws CommonException
     */
    public String decodeToJsonString(String base64String) throws UnsupportedEncodingException, CommonException {
        if (StringUtils.isEmpty(base64String)) {
            throw new CommonException("注册失败,参数p缺失!");
        }

        String json = new String(decoder.decode(base64String), "UTF-8");

        if (StringUtils.isEmpty(json)) {
            throw new CommonException("注册失败,json为空!");
        }
        return json;
    }
}
