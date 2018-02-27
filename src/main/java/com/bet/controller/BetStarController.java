package com.bet.controller;

import com.bet.model.entities.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Extra on 2018/2/24.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
@Controller
public class BetStarController extends BaseController {
    @RequestMapping("api/test")
    @ResponseBody
    public String test(){
        Result  result  = new Result();
        result.setRst("1");
        result.setDetail("OK");
        Result.DataBean dataBean = new Result.DataBean();
        dataBean.setSn("123456");
        dataBean.setDatetime(System.currentTimeMillis());
        result.setData(dataBean);
        return responseRR(result);
    }


}
