package com.zzx.login.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.zzx.login.constant.Constant;
import com.zzx.login.dao.UserDao;
import com.zzx.login.dao.UserLoginLogDao;
import com.zzx.login.entity.RegisterVo;
import com.zzx.login.entity.UserDto;
import com.zzx.login.exception.CommonException;
import com.zzx.login.service.TokenService;
import com.zzx.login.service.UserService;
import com.zzx.login.util.Base64Util;
import com.zzx.login.util.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Value("${account.length}")
    private int length;

    @Autowired
    Base64Util base64Util;

    @Autowired
    UserDao userDao;

    @Autowired
    UserLoginLogDao userLoginLogDao;

    @Autowired
    TokenService tokenService;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 注册
     */
    @Override
    public void register(String encryptedString) throws CommonException, UnsupportedEncodingException {
        String json = base64Util.decodeToJsonString(encryptedString);
        RegisterVo vo = JSON.parseObject(json, RegisterVo.class);
        if (vo == null) {
            throw new CommonException("注册失败,请携带参数!");
        }
        if (vo.getAccount().length() > length) {
            throw new CommonException("注册账号长度不能超过" + length + "!");
        }
        if (!StringUtils.isEmpty(vo.getAccount()) && !StringUtils.isEmpty(vo.getPassword())) {
            // 先查询是否已经注册
            Integer id = userDao.queryExistAccount(vo.getAccount());
            if (id != null) {
                throw new CommonException("注册失败,账户已存在!");
            }
            userDao.registerAccount(vo.getAccount(), vo.getPassword(), vo.getExtensionParam());
        }
    }

    /**
     * 登录
     * @return
     */
    @Override
    public String login(String encryptedString, String ipAddress) throws CommonException, UnsupportedEncodingException {
        String json = base64Util.decodeToJsonString(encryptedString);
        RegisterVo vo = JSON.parseObject(json, RegisterVo.class);
        String account = vo.getAccount();

        UserDto user = userDao.queryUserByAccount(account);
        if (user == null || !user.getPassword().equalsIgnoreCase(vo.getPassword())) {
            userLoginLogDao.insertLoginLog(account, ipAddress, Constant.LOGIN.FAILURE);
            throw new CommonException("登录名或者密码错误");
        }
        // 记录登录日志
        userLoginLogDao.insertLoginLog(account, ipAddress, Constant.LOGIN.SUCCESS);
        // 把token放置到redis中，并设置一个过期时间 （从redis中查不到这个token，则认为是过期。如果查出来不一致，则校验不成功。）
        return removeAndCreateToken(account);
    }

    /**
     * token的校验
     *
     * @param token
     * @return
     */
    @Override
    public String checkToken(String token) throws CommonException {
        if (StringUtils.isEmpty(token)) {
            throw new CommonException("请携带token!");
        }
        Claims claims = null;
        try {
            claims = tokenService.parseJWT(token);
        }catch (ExpiredJwtException ee) {
            throw new CommonException("token过期!请重新登录!");
        }catch (Exception e) {
            throw new CommonException("token解析异常!");
        }
        String account = (String) claims.get("account");
        String key = account + Constant.REDIS_KEY.TOKEN;
        String redisToken = redisUtil.get(key);
        boolean flag = claims.getExpiration().before(new Date()); // true:过期   false:没过期
        if (!redisUtil.exists(key) || !token.equals(redisToken) || flag) { // 1.redis中没有则是过期 2.有的话则看看是否相同（如果有不同的人登录了同一个账号，则token不一致，以redis上的最新的token为准）3.token的时间已经到了
            throw new CommonException("token过期!请重新登录!");
        }
        // 没过期就重新创建一个，并且把之前的token给设置过期
        return removeAndCreateToken(account);
    }

    private String removeAndCreateToken(String account) {
        String key = account + Constant.REDIS_KEY.TOKEN;
        redisUtil.remove(key);
        String newToken = tokenService.createToken(account);
        redisUtil.set(key, newToken, 3600L); // 时间应该设置的和配置的一样，不移除了再插入的话，过期时间还是会按照以前的时间算
        return newToken;
    }
}
