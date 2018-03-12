package com.bet.service;

import com.bet.model.entities.DataBean;
import com.bet.model.entities.LoginBean;

/**
 * Created by Extra on 2018/2/28.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
public interface LoginService  {
    LoginBean getBetUser(String sn,String password);

    LoginBean getCheckSerialNumber(String sn);

    void  setToken (String sn,String token);

    LoginBean  getMakeBean(String sn,String token);

    DataBean getCreditLimit(String sn);

    void changePassword(String new_password, String sn);
}
