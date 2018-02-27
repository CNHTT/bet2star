package com.bet.controller;

import com.bet.model.entities.Result;
import com.bet.model.response.ResponseObj;
import com.bet.utils.DataUtils;
import com.bet.utils.GsonUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.log4j.Logger;

/**
 * Created by CT on 2017/7/15.
 */
public class BaseController {

    protected Logger log = Logger.getLogger(BaseController.class);

    protected final static String DATE_FORMATE = "yyyy-MM-dd";


    /**
     * 返回数据
     * @param obj
     * @return json
     */
    public String responseResult(Object obj){
        ResponseObj<Object> resp = new ResponseObj<Object>();
        if (DataUtils.isEmpty(obj)){
            resp.setCode(ResponseObj.EMPUTY);
            resp.setMsg(ResponseObj.EMPUTY_STR);
        }else {
            resp.setCode(ResponseObj.OK);
            resp.setMsg(ResponseObj.OK_STR);
            resp.setData(obj);
        }
        log.info("system.out: "+new GsonUtils().toJson(resp));
        return new GsonUtils().toJson(resp);
    }
    /**
     * 返回数据
     * @param obj
     * @return json
     */
    public String responseRR(Result obj){

        log.info("system.out: "+new GsonUtils().toJson(obj));
        return new GsonUtils().toJson(obj);
    }
    /**
     * 返回数据
     * @param obj
     * @return json
     */
    public String responseSuccess(Object obj,String str){
        ResponseObj<Object> resp = new ResponseObj<Object>();
        if (DataUtils.isEmpty(obj)){
            resp.setCode(ResponseObj.EMPUTY);
            resp.setMsg(ResponseObj.EMPUTY_STR);
        }else {
            resp.setCode(ResponseObj.OK);
            resp.setMsg(str);
            resp.setData(obj);
        }
        log.info("system.out: "+new GsonUtils().toJson(resp));
        return new GsonUtils().toJson(resp);
    }

    /**
     * 返回数据
     * @return json
     */
    public String responseSuccess(String str){
        ResponseObj<Object> resp = new ResponseObj<Object>();
            resp.setCode(ResponseObj.OK);
            resp.setMsg(str);
        log.info("system.out: "+new GsonUtils().toJson(resp));
        return new GsonUtils().toJson(resp);
    }


    public String responseFail(String error){
        ResponseObj<String> resp = new ResponseObj<String>();
        resp.setMsg(error);
        resp.setCode(ResponseObj.FAILED);
        log.info("system.out: "+new GsonUtils().toJson(resp));
        return new GsonUtils().toJson(resp);
    }


    public String returnFail(String sn){
        Result result = new Result();
        result.setSn(result.getSn());
        result.setRst("1003");
        result.setDetail("Your request is failed.");
        return new GsonUtils().toJson(result);
    }


    String returnValidy(String sn) {
        Result result = new Result();
        result.setSn(result.getSn());
        result.setRst("1001");
        result.setDetail("Validy date is not defined.");
        return new GsonUtils().toJson(result);
    }
}
