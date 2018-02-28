package com.bet.service.impl;

import com.bet.dao.LoginDao;
import com.bet.model.entities.LoginBean;
import com.bet.service.LoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.ws.RespectBinding;

/**
 * Created by Extra on 2018/2/28.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    @Resource
   private LoginDao loginDao;

    @Override
    public LoginBean getBetUser(String sn, String password) {
        return loginDao.selectUser(sn,password) ;
    }

    @Override
    public LoginBean getCheckSerialNumber(String sn) {
        return loginDao.selectSerialExist(sn);
    }

    @Override
    public void setToken(String sn, String token) {
        loginDao.updateToken(sn,token);
    }

    @Override
    public LoginBean getMakeBean(String sn, String token) {
        return null;
    }
}
