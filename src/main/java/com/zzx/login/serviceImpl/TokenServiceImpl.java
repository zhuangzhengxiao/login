package com.zzx.login.serviceImpl;


import com.zzx.login.config.TokenProperty;
import com.zzx.login.service.TokenService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;


@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    TokenProperty tokenProperty;

    @Override
    public Claims parseJWT(String token) throws ExpiredJwtException {

            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(tokenProperty.getKey()))
                    .parseClaimsJws(token).getBody();
    }

    @Override
    public String createToken(String account) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //添加Token过期时间
        long expMillis = nowMillis + tokenProperty.getExpire() * 1000;
        Date exp = new Date(expMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenProperty.getKey());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("account", account)
                .claim("timestamp",nowMillis)
                .setExpiration(exp)
                .setNotBefore(now)
                .signWith(signatureAlgorithm, signingKey);
        //生成JWT
        return builder.compact();
    }
}
