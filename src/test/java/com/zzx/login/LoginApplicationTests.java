package com.zzx.login;

import com.zzx.login.service.TokenService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginApplicationTests {

    /**
     * 当前时间：Fri Sep 25 15:40:26 CST 2020当前时间戳:1601019626191
     * 过期时间：Fri Sep 25 16:40:26 CST 2020当前时间戳:1601023226191
     * 当前时间：Fri Sep 25 15:40:26 CST 2020当前时间戳:1601019626308
     * 过期时间：Fri Sep 25 16:40:26 CST 2020当前时间戳:1601023226308
     */

    @Autowired
    TokenService tokenService;

    @Test
    public void contextLoads() {
        System.out.println(getToken("qwer6",1601019626191L,1601023226191L));
        System.out.println(getToken("qwer6",1601019626308L,1601023226308L));

    }

    private String getToken(String account,long nowMillis,long expMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

//        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //添加Token过期时间
//        long expMillis = nowMillis + 3600 * 1000;
        Date exp = new Date(expMillis);
        System.out.println("当前时间：" + now + "当前时间戳:" +nowMillis);
        System.out.println("过期时间：" + exp + "当前时间戳:" +expMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("bW9jaGVuZw==");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("account", account)
                .setExpiration(exp)
                .setNotBefore(now)
                .signWith(signatureAlgorithm, signingKey);
        //生成JWT
        return builder.compact();
    }

}
