package com.bet.controller;

import com.bet.model.entities.*;
import com.bet.service.LoginService;
import com.bet.service.MakeBetService;
import com.bet.utils.CalculateUtils;
import com.bet.utils.DataUtils;
import com.bet.utils.GsonUtils;
import com.bet.utils.TimeUtils;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static java.awt.SystemColor.info;

/**
 * uAiqwVwjJ8-i
 * Created by Extra on 2018/2/24.
 * GitHub cnhttt@163.comx
 * Work to SZFP
 */
@Controller
@RequestMapping("api")
public class MakeBetController extends BaseController {

    @Resource
    private LoginService loginService;

    @Resource
    private MakeBetService makeBetService;


    private boolean npa =false;
    private boolean group =false;


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
                return returnValidy(sn);
            }
            npa = false;
            group = false;

            result.setSn(sn);
            result.setRst("1");

           DataBean data = new DataBean();
            data.setWeek(String.valueOf(TimeUtils.stringForWeek()));
            data.setPlayed_date(TimeUtils.milliseconds2String(System.currentTimeMillis()));
            data.setClose_date(TimeUtils.date2String(TimeUtils.getWeekStartDate()));
            data.setValidity(TimeUtils.plusDay(TimeUtils.getWeekStartDate(),10));

            int tx_id = makeBetService.selectCountTxid();
            data.setTx_id(String.valueOf(tx_id+1));

            int amount = 0;

            MakeBet item;
            MakeBet<List<GroupBean>> listMakeBet;
            List<MakeBet> list = request.getData();





            for (int i = 0; i <list.size() ; i++) {






                    item = list.get(i);
                 int  itemAmount = Integer.parseInt(item.getAmount());
                    try {
                     switch (item.getType()){
                        case "1":

                            npa =true;
                            String game = (String) item.getGames();
                            if (Integer.valueOf(item.getUnder())>game.split(",").length){
                                item.setStatus("1");
                                item.setDescription("Your stake must be reached minimum per line requirement.");
                                continue;
                            }

                            item.setApl(String.valueOf(CalculateUtils.calculateNapOrPerm(item.getUnder(),game.split(",").length,item.getAmount())));



                            break;

                        case "2":

                            String game2 = (String) item.getGames();
                            item.setApl(String.valueOf(CalculateUtils.calculateCombination(item.getUnder(),game2.split(",").length,item.getAmount())));
                            break;

                        case "3":
                            group =true;
                            listMakeBet  = list.get(i);
                            List<LinkedTreeMap<String,String>>  groupBeans;
                            groupBeans = (List<LinkedTreeMap<String, String>>) item.getGames();

                            listMakeBet.setApl(String.valueOf(CalculateUtils.calculateGrouping(groupBeans,listMakeBet.getAmount())));
                            break;
                    }

                    if (itemAmount<49){
                        item.setStatus("1");
                        item.setDescription("Your stake amount should be more than 50.");
                    }else {
                        amount = amount + itemAmount;
                        item.setStatus("0");
                        item.setDescription("Success");
                        item.setBet_id(CalculateUtils.setBetId());
                    }
                }catch (Exception e){
                        item.setStatus("1");
                        item.setDescription("Your stake must be reached minimum per line requirement.");
                }

            }
            data.setTotal_stake(String.valueOf(amount));
            data.setBets(list);


            if (npa&&group) return returnFail(sn);

            result.setData(data);


            result.setDetail("Success");


            //添加数据库
            makeBetService.addMakeBet(data,sn);

            for (int i = 0; i <list.size() ; i++) {


                item = list.get(i);
                if (item.getStatus().equals("0"))
                try {
                    switch (item.getType()) {
                        case "1":
                            makeBetService.addBet((MakeBet<String>) item,sn,data.getTx_id());
                            break;

                        case "2":
                            makeBetService.addBet((MakeBet<String>)item,sn,data.getTx_id());
                            break;

                        case "3":
                            listMakeBet = list.get(i);
                            List<LinkedTreeMap<String, String>> groupBeans;
                            groupBeans = (List<LinkedTreeMap<String, String>>) item.getGames();
                            makeBetService.addBets((MakeBet<String>)item,sn,data.getTx_id());
                                    for (LinkedTreeMap<String,String> g:groupBeans) {
                                        makeBetService.addGroupBean(data.getTx_id(),item.getBet_id(),g.get("group"),g.get("number"),g.get("item"),sn);
                                    }

                            break;
                    }


                }catch (Exception e){

                    log.info(e.getMessage());
                }

            }

        }catch (Exception e){
            log.info(e.getMessage());
            return returnFail(sn);
        }

        return responseRR(result);
    }


    @RequestMapping("setWeek")
    @ResponseBody
    public String setWeek(@RequestBody String s){

        MakeBetBean request = null;
        request  = new GsonUtils().fromJson(s,MakeBetBean.class);
        String sn = null;
        String token = null;

        LoginBean loginBean ;
        sn = request.getSn();
        token = request.getToken();

        //check user
        loginBean = loginService.getMakeBean(sn,token);

        if (DataUtils.isEmpty(loginBean)){
            return returnValidy(sn);
        }
        return returnValidy(sn);
    }


}
