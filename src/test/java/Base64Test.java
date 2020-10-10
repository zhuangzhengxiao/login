import com.alibaba.fastjson.JSON;
import com.zzx.login.entity.Result;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Test {

    @Test
    public void base64Test() throws UnsupportedEncodingException {
        // 1.用base64解码
        // 2.将字符串转成对象
        final Base64.Decoder decoder = Base64.getDecoder();
        //解码
        String json = new String(decoder.decode("ew0KICAgICJjb2RlIjogMCwNCiAgICAibXNnIjogInRva2Vu6L+H5pyfIeivt+mHjeaWsOeZu+W9lSEiLA0KICAgICJkYXRhcyI6IG51bGwNCg=="), "UTF-8");
        Result result = JSON.parseObject(json, Result.class);

        System.out.println(result.getCode());
        System.out.println(result.getMsg());
        System.out.println(result.getDatas());

    }
}
