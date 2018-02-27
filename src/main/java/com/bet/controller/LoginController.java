package com.bet.controller;

import com.bet.model.entities.LoginBean;
import com.bet.model.entities.Result;
import com.bet.utils.GsonUtils;
import com.bet.utils.JWTUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Extra on 2018/2/24.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
@Controller
@RequestMapping("api/login")
public class LoginController extends BaseController {


    /**
     * login  --token
     * @param s
     * @return
     */
    @RequestMapping(value = "verify",consumes ="application/json")
    @ResponseBody
    public String verify(@RequestBody String s, HttpServletRequest request){


        Result result = new Result();
        LoginBean loginBean =new  GsonUtils().toBean(s,LoginBean.class);
        String token;

        try {
             token = JWTUtil.createJWT(loginBean.getSn(),loginBean.getPassword(),System.currentTimeMillis());
             //check user

            result .setSn(loginBean.getSn());
            result .setRst("1");
            result .setToken(token);
            result .setDetail("Success");
            Result.DataBean dataBean =  new Result.DataBean();
            dataBean.setDefault_type("1");
            dataBean.setDefault_sort("1");
            dataBean.setDefault_under("1");

        } catch (Exception e) {
            e.printStackTrace();
            token = "0";
        }
        return responseRR(result);
    }
}
