package com.zzx.login.dao;

import com.zzx.login.entity.UserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    void registerAccount(@Param("account") String account, @Param("password") String password, @Param("phone") String phone);

    Integer queryExistAccount(@Param("account") String account);

    UserDto queryUserByAccount(@Param("account") String account);
}
