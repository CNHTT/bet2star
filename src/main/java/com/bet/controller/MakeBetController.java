package com.bet.controller;

import com.bet.model.entities.LoginBean;
import com.bet.model.entities.MakeBetBean;
import com.bet.model.entities.Result;
import com.bet.service.LoginService;
import com.bet.utils.DataUtils;
import com.bet.utils.GsonUtils;
import com.bet.utils.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * uAiqwVwjJ8-i
 * Created by Extra on 2018/2/24.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
@Controller
public class MakeBetController extends BaseController {

    @Resource
    private LoginService loginService;


    @RequestMapping(value = "makebet")
    @ResponseBody
    public String makebet(@RequestBody String s){

        MakeBetBean request = null;
        List<MakeBetBean.Data> dataBean = null;

        String sn = null;
        String token = null;

        LoginBean loginBean ;


        Result result  = new Result();
        try {
            request  = new GsonUtils().fromJson(s,MakeBetBean.class);

            sn = request.getSn();
            token = request.getToken();

            //check user
            loginBean = loginService.getMakeBean(sn,token);

            if (DataUtils.isEmpty(loginBean)){
                return returnValidy(request.getSn());
            }

            result.setSn(sn);
            result.setRst("1");

            Result.DataBean data = new Result.DataBean();
            data.setWeek(String.valueOf(TimeUtils.stringForWeek()));
            data.setPlayed_date(TimeUtils.milliseconds2String(System.currentTimeMillis()));
            data.setClose_date(TimeUtils.date2String(TimeUtils.getWeekStartDate()));
            data.setValidity(TimeUtils.plusDay(TimeUtils.getWeekStartDate(),10));








        }catch (Exception e){
            return returnFail(sn);
        }

        return new GsonUtils().toJson(dataBean);
    }

}
