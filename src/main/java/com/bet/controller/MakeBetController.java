package com.bet.controller;

import com.bet.model.entities.MakeBetBean;
import com.bet.model.entities.Result;
import com.bet.utils.GsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Extra on 2018/2/24.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
@Controller
public class MakeBetController extends BaseController {

    @RequestMapping(value = "makebet")
    @ResponseBody
    public String makebet(@RequestBody String s){

        MakeBetBean request = null;
        List<MakeBetBean.Data> dataBean = null;

        Result result  = new Result();
        try {
            request  = new GsonUtils().fromJson(s,MakeBetBean.class);
            //check user

            if (!request.getSn().equals("SN_001")){
                return returnValidy(request.getSn());
            }
            request.getSn();
            request.getToken();
            dataBean = request.getData();
        }catch (Exception e){
            return returnFail(request.getSn());
        }

        return new GsonUtils().toJson(dataBean);
    }

}
