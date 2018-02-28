package com.bet.dao;

import com.bet.model.entities.LoginBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Extra on 2018/2/28.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
@Repository
public interface LoginDao {
     LoginBean selectUser(@Param("sn")String sn,@Param("password")String password);
     LoginBean selectSerialExist(String sn);
     void updateToken (@Param("sn")String sn,@Param("token")String token);

     LoginBean selectUserToken(@Param("sn")String sn,@Param("token")String token);
}
