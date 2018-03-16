package com.bet.controller;

import com.bet.model.entities.DataBean;
import com.bet.model.entities.LoginBean;
import com.bet.model.entities.Result;
import com.bet.service.LoginService;
import com.bet.utils.DataUtils;
import com.bet.utils.GsonUtils;
import com.bet.utils.JWTUtil;
import com.bet.utils.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Extra on 2018/2/24.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
@Controller
@RequestMapping("api/login")
public class LoginController extends BaseController {


    @Resource
    private LoginService loginService;

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
        String sn = loginBean.getSn();
        String password=loginBean.getPassword();
        String token;
            try {
             token = JWTUtil.createJWT(loginBean.getSn(),loginBean.getPassword(),System.currentTimeMillis());


             //check serial number
             loginBean = loginService.getCheckSerialNumber(loginBean.getSn());
             if (DataUtils.isEmpty(loginBean)){
                 result.setSn(sn);
                 result.setDetail("The serial number does not exist");
                 result.setRst("1001");
                 return responseRR(result);
             }

             //check user
            loginBean = loginService.getBetUser(sn,password);
             if (DataUtils.isEmpty(loginBean)){

                 result.setSn(sn);
                 result.setDetail("pass_error:wrong password");
                 result.setRst("1002");
                 return responseRR(result);
             }



            result .setSn(loginBean.getSn());
            result .setRst("1");
            
            result .setToken(token);
            loginService.setToken(sn,token);
            result .setDetail("Success");
            DataBean dataBean =  new DataBean();
            dataBean.setDefault_type(loginBean.getDefault_type());
            dataBean.setDefault_sort(loginBean.getDefault_sort());
            dataBean.setDefault_under(loginBean.getDefault_under());
            dataBean.setDatetime(String.valueOf(TimeUtils.getCurTimeMills()));
            result.setData(dataBean);
        } catch (Exception e) {
                log.info("system.out: "+e.getMessage());
            return  returnFail(sn);
        }
        return responseRR(result);
    }
}
