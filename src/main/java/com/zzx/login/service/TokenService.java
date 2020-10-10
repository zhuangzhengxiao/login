package com.zzx.login.service;

import com.fasterxml.jackson.core.JsonParseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public interface TokenService {
    Claims parseJWT(String jsonWebToken) throws ExpiredJwtException;

    String createToken(String account);
}
