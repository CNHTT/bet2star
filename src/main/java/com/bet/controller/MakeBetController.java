package com.bet.controller;

import com.bet.dao.PosFunctionDao;
import com.bet.model.entities.*;
import com.bet.service.LoginService;
import com.bet.service.MakeBetService;
import com.bet.service.PosFunctionService;
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
import java.util.*;

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

    @Resource
    private PosFunctionService posFunctionService;


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
            data.setClose_date(TimeUtils.date2String(TimeUtils.getWeekStartDate1()));
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

                            String game = (String) item.getGames();
                            if (Integer.valueOf(item.getUnder())>game.split(",").length){
                                item.setStatus("1");
                                item.setDescription("Your stake must be reached minimum per line requirement.");
                                continue;
                            }

                            item.setApl(String.valueOf(CalculateUtils.calculateNapOrPerm(item.getUnder(),game.split(",").length,item.getAmount())));

                            npa =true;


                            break;

                        case "2":

                            String game2 = (String) item.getGames();
                            item.setApl(String.valueOf(CalculateUtils.calculateCombination(item.getUnder(),game2.split(",").length,item.getAmount())));
                            if (itemAmount<49){
                                item.setStatus("1");
                                item.setDescription("Your stake amount should be more than 50.");
                            }else
                            npa =true;
                            break;

                        case "3":
                            group =true;
                            int under=0;
                            listMakeBet  = list.get(i);
                            List<LinkedTreeMap<String,String>>  groupBeans;
                            groupBeans = (List<LinkedTreeMap<String, String>>) item.getGames();
                            if (groupBeans ==null&&groupBeans.size()==0){
                                item.setStatus("1");
                                item.setDescription("Your stake must be reached minimum per line requirement.");
                                continue;
                            }


                            List<String> games = new ArrayList<>();
                            for (LinkedTreeMap<String,String> g:groupBeans) {
                                games.addAll(Arrays.asList(g.get("item").split(",")));
                                under = under+Integer.valueOf(g.get("number"));
                            }

                            if (!cheakIsRepeat(games)){
                                item.setStatus("1");
                                item.setDescription("Your stake must be reached minimum per line requirement.1");continue;
                            }else {
                                for (int j = 0; j <list.size() ; j++) {
                                    LinkedTreeMap<String,String> groupBean = groupBeans.get(i);
                                    if (Integer.valueOf(groupBean.get("number"))>groupBean.get("item").split(",").length){
                                        item.setStatus("1");
                                        item.setDescription("Your stake must be reached minimum per line requirement.2");continue;
                                    }
                                }


                                log.info(under +"         " + item.getUnder());
                                if (under!=Integer.valueOf(item.getUnder())){
                                    item.setStatus("1");
                                    item.setDescription("Your stake must be reached minimum per line requirement.3");continue;
                                }

                                listMakeBet.setApl(String.valueOf(CalculateUtils.calculateGrouping(groupBeans,listMakeBet.getAmount())));
                            }
                            npa =true;
                            break;
                    }

                    if (itemAmount<49){
                        item.setStatus("1");
                        item.setDescription("Your stake amount should be more than 50.");
                    }else {
                        if (!item.equals("1")){
                            amount = amount + itemAmount;
                            item.setStatus("0");
                            item.setDescription("Success");
                            item.setBet_id(CalculateUtils.setBetId());
                        }
                        }
                }catch (Exception e){
                        item.setStatus("1");
                        item.setDescription("Your stake must be reached minimum per line requirement. E");
                }

            }

            if (!npa) return returnFail(sn);
            data.setTotal_stake(String.valueOf(amount));
            data.setBets(list);



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


    /*
        * 判断数组中是否有重复的值
        */
    public  boolean cheakIsRepeat(List<String> l) {
        boolean f= true;
        Set<String> s = new HashSet<String>();
        for (String str : l) {
            boolean b = s.add(str);
            if(!b){
               f =  false;
            }
        }
        log.info(f);
        return f;
    }









    private boolean calculate = true;

    @RequestMapping("setWeek")
    @ResponseBody
    public String setWeek(@RequestBody String s){

        MakeBetBean request = null;
        request  = new GsonUtils().fromJson(s,MakeBetBean.class);
        String sn = null;
        String token = null;
        String week =  request.getWeek() ==null?String.valueOf(TimeUtils.stringForWeek()): request.getWeek();

        log.info( "week      " + week);

        LoginBean loginBean ;
        sn = request.getSn();
        token = request.getToken();
        if (true){
            calculate = false;
            try {

                //check user
                loginBean = loginService.getMakeBean(sn,token);

                if (DataUtils.isEmpty(loginBean)){
                    return returnValidy(sn);
                }

                //查询  本周所有的 投注
                List<String> lists = request.getWeeks();            // 本周的结果
                List<MakeBet> bets = makeBetService.getWeek(week);  // All  bet

                if (posFunctionService.findWeekResult(week)!=null)
                    makeBetService.setWeek(week,new GsonUtils().toJson(lists));
                else
                    makeBetService.addWeek(week,new GsonUtils().toJson(lists));
                /**
                 *
                 */
                for (int i = 0; i < bets.size() ; i++) {
                    MakeBet  bet = bets.get(i);



                    if (bet!=null){
                        int  produce=0;
                        List<String>  games = new ArrayList<>();
                        if(bet.getType().equals("3")){
                            List<GroupBean> groupBeans = makeBetService.getReprintGroupBeans(bet.getBet_id());
                            if (groupBeans==null)continue;
                            for (GroupBean group: groupBeans) {
                                games.addAll(Arrays.asList(group.getItem().split(",")));
                            }
                            produce = produceWeek(lists,games);
                        }else {
                            if (bet.getGames()==null)continue;
                            games = Arrays.asList(bet.getGames().toString().split(",")) ;
                            produce = produceWeek(lists,games);
                        }

                        log.info(games.toString());


                        log.info(bet.getBet_id() +  "        "+produce);

                        //大于三分的才计算

                        if (produce>2){
                            switch (bet.getSort()){
                                case  "1": //100
                                    bet.setWinAmount(produceAmount(Integer.parseInt(bet.getAmount()),games.size(),100,produce));
                                    break;
                                case  "2"://80
                                    bet.setWinAmount(produceAmount(Integer.parseInt(bet.getAmount()),games.size(),80,produce));
                                    break;
                                case  "3"://40
                                    bet.setWinAmount(produceAmount(Integer.parseInt(bet.getAmount()),games.size(),40,produce));
                                    break;
                                case  "4"://60
                                    bet.setWinAmount(produceAmount(Integer.parseInt(bet.getAmount()),games.size(),60,produce));
                                    break;
                            }

                            //修改中奖金额

                            makeBetService.updateAmount(bet);


                        }
                    }

                }
                calculate = true;
            }catch (Exception e){
                log.debug(e.getMessage());
                log.debug(e.toString());
                calculate = true;
                return     returnOOOOOO(sn,"请勿提交错误数据")   ;
            }
        }else {
            return  returnOOOOOO(sn,"  \"计算中  ，请勿反复提交\"");
        }

        return returnOOOOOO(sn,"出奖、结束");
    }

    /**
     *
     * @param amount
     * @param size
     * @param i1
     * @param produce
     * @return

    //amount /  Lins(games.size)    *    80 / 60  40  20  1       *Lines(produce)

    //     (3*(3-1)*(3-2))/6

     */
    private String produceAmount(int amount, int size, int i1, int produce) {
        return  String.valueOf( amount/ line(size)   *  line(produce)  * i1) ;
    }

    private int line(int size) {
        return (size*(size-1)*(size-2))/6;
    }


    /**
     *
     * @param weeks    每周计算产生的结果
     * @param games    每个游戏选择的数控
     * @return
     */
    public int produceWeek(List<String> weeks,List<String> games) {

        // 用户输奖号码相同的总数
        int sum = 0;
        if (weeks.size()>games.size()){
            for (int i = 0; i < weeks.size() ; i++) {
                String  iii = weeks.get(i);
                // 判断输入的数字与数组中数字的关系
                for (int j = 0; j < games.size(); j++) {
                    if (iii.equals(games.get(j))) {
                        sum += 1;
                    } else {
                        sum += 0;
                    }
                }
            }
        }else {
            for (int i = 0; i < games.size() ; i++) {
                String  iii = games.get(i);
                for (int j = 0; j < weeks.size(); j++) {
                    if (iii.equals(weeks.get(j))) {
                        sum += 1;
                    } else {
                        sum += 0;
                    }
                }
            }


        }


        return sum;

    }


}
