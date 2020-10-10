package com.zzx.login.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginLogDao {

    void insertLoginLog(@Param("account") String account, @Param("ip") String ip, @Param("status") int status);

}
