package com.bet.controller;

import com.bet.model.entities.*;
import com.bet.service.LoginService;
import com.bet.service.MakeBetService;
import com.bet.service.PosFunctionService;
import com.bet.utils.DataUtils;
import com.bet.utils.GsonUtils;
import com.bet.utils.TimeUtils;
import jdk.jfr.events.ExceptionThrownEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("api/posfunction")
public class PosFunctionController extends BaseController {


    @Resource
    private LoginService loginService;

    @Resource
    private MakeBetService makeBetService;

    @Resource
    private PosFunctionService posFunctionService;


    @RequestMapping("result")
    @ResponseBody
    public String result(@RequestBody String s) {

        MakeBetBean request = null;
        String sn = null;
        String week = null;
        String token = null;
        LoginBean loginBean;


        Result result = new Result();
        try {
            request = new GsonUtils().fromJson(s, MakeBetBean.class);
            sn = request.getSn();
            token = request.getToken();
            //check user
            loginBean = loginService.getMakeBean(sn, token);

            if (DataUtils.isEmpty(loginBean)) {
                return returnValidy(sn);
            }
            week = request.getWeek();

            DataBean data = new DataBean();
            String drawn = posFunctionService.findWeekResult(week);
            if (drawn != null) {
                result.setDetail("Success");
                result.setSn(sn);
                result.setRst("1");
                data.setWeek(week);
                data.setDrawn(new GsonUtils().fromJson(drawn,List.class));
                result.setData(data);
                return responseRR(result);
            } else {
                result.setDetail("Success");
                result.setSn(sn);
                result.setRst("1");
                data = new DataBean();
                data.setWeek(week);
                data.setDrawn(new ArrayList<String>());
                result.setData(data);
                return responseRR(result);
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            return returnFail(sn);
        }

    }

    @RequestMapping("reprint")
    @ResponseBody
    public String reprint(@RequestBody String s) {
        MakeBetBean request = null;
        String sn = null;
        String token = null;
        LoginBean loginBean;

        Result result = new Result();
        try {
            request = new GsonUtils().fromJson(s, MakeBetBean.class);
            sn = request.getSn();
            token = request.getToken();
            //check user
            loginBean = loginService.getMakeBean(sn, token);
            if (DataUtils.isEmpty(loginBean)) {
                return returnValidy(sn);
            }
            //查询记录
            DataBean data = makeBetService.getReprintData(sn);
            if (data == null) {
                result.setDetail("Last transaction does not exist.");
                result.setSn(sn);
                result.setRst("1001");
                return responseRR(result);
            }
            List<MakeBet> bets = makeBetService.getReprintMakeBet(data.getTx_id());
            if (bets == null) {

                result.setDetail("No bet exists");
                result.setSn(sn);
                result.setRst("1002");
                return responseRR(result);
            }
            for (MakeBet bet : bets) {
                if (bet.getType().equals("3")) {
                    List<GroupBean> groupBeans = makeBetService.getReprintGroupBeans(bet.getBet_id());
                    bet.setGames(groupBeans);
                }
            }
            data.setBets(bets);

            result.setData(data);
            result.setDetail("Success");
            result.setSn(sn);
            result.setRst("1");

            return responseRR(result);

        } catch (Exception e) {
            log.info(e.getMessage());
            return returnFail(sn);
        }
    }


    @RequestMapping("winlist")
    @ResponseBody
    public String winList(@RequestBody String s) {
        MakeBetBean request = null;
        String sn = null;
        String token = null;
        String week = null;
        LoginBean loginBean;

        Result result = new Result();
        try {
            request = new GsonUtils().fromJson(s, MakeBetBean.class);
            sn = request.getSn();
            token = request.getToken();
            week  = request.getWeek();

            //check user
            loginBean = loginService.getMakeBean(sn, token);
            if (DataUtils.isEmpty(loginBean)) {
                return returnValidy(sn);
            }
            DataBean dataBean = new DataBean();
            dataBean.setAgent("123");
            dataBean.setOperator("operator1");

            if(week ==null) week = String.valueOf(TimeUtils.stringForWeek());
            List<WinListBean>  listBeans = makeBetService.getWinList(week,sn);
            if (listBeans ==null) {
                listBeans = new ArrayList<>();
                dataBean.setTotal("0");
            }else {
                dataBean.setTotal(makeBetService.getWinTotal(week,sn));
            }
            if (listBeans.size()==0)
                dataBean.setTotal("0");
            dataBean.setWinlist(listBeans);
            dataBean.setWeek(week);
            dataBean.setClose_date(TimeUtils.date2String(TimeUtils.getWeekStartDate()));
            result.setData(dataBean);
            result.setDetail("Success");
            result.setSn(sn);
            result.setRst("1");

            return responseRR(result);
        } catch (Exception e) {
            log.info(e.getMessage());
            return returnFail(sn);
        }
    }


    @RequestMapping("report")
    @ResponseBody
    public String report(@RequestBody String s) {
        MakeBetBean request = null;
        String sn = null;
        String token = null;
        LoginBean loginBean;

        Result result = new Result();
        try {
            request = new GsonUtils().fromJson(s, MakeBetBean.class);
            sn = request.getSn();
            token = request.getToken();
            //check user
            loginBean = loginService.getMakeBean(sn, token);
            if (DataUtils.isEmpty(loginBean)) {
                return returnValidy(sn);
            }

            DataBean dataBean = new DataBean();

            dataBean = makeBetService.getReportData(sn);

            if (dataBean ==null||dataBean.getTotal_payable() == null || dataBean.
                    getTotal_payable().equals("0")) {
                dataBean = new DataBean();
                dataBean.setOdd_summary(new ArrayList<OddSummary>());
                dataBean.setTotal_payable("0");
                dataBean.setTotal_winning("0");
                dataBean.setBalance_comp("0");
                dataBean.setBalance_agen("0");
                dataBean.setStatus("Red");

                result.setData(dataBean);
                result.setDetail("Success");
                result.setSn(sn);
                result.setRst("1");

                return responseRR(result);
            }

            List<OddSummary> oddSummaries = new ArrayList<>();
            OddSummary oddSummary1 = makeBetService.getOddSummary(sn, "1");
            if (oddSummary1.getGross()==null||!oddSummary1.getGross().equals("0")) {
                oddSummary1.setSort("1");
                oddSummary1.setCommission("0");
                oddSummaries.add(oddSummary1);
            }
            OddSummary oddSummary2 = makeBetService.getOddSummary(sn, "2");
            if (oddSummary2.getGross()==null||!oddSummary2.getGross().equals("0")) {
                oddSummary2.setSort("2");
                oddSummary2.setCommission("0");
                oddSummaries.add(oddSummary2);
            }
            OddSummary oddSummary3 = makeBetService.getOddSummary(sn, "3");
            if (oddSummary3.getGross()==null||!oddSummary3.getGross().equals("0")) {
                if (oddSummary3.getGross()==null)oddSummary3.setGross("0");
                oddSummary3.setSort("3");
                oddSummary3.setCommission("0");
                oddSummaries.add(oddSummary3);
            }

            dataBean.setOdd_summary(oddSummaries);
            dataBean.setStatus("Blue");
            result.setData(dataBean);
            result.setDetail("Success");
            result.setSn(sn);
            result.setRst("1");

            return responseRR(result);
        } catch (Exception e) {
            log.info(e.getMessage());
            return returnFail(sn);
        }
    }

    @RequestMapping("creditlimit")
    @ResponseBody
    public String creditLimit(@RequestBody String s) {
        MakeBetBean request = null;
        String sn = null;
        String token = null;
        LoginBean loginBean;

        Result result = new Result();
        try {
            request = new GsonUtils().fromJson(s, MakeBetBean.class);
            sn = request.getSn();
            token = request.getToken();
            //check user
            loginBean = loginService.getMakeBean(sn, token);
            if (DataUtils.isEmpty(loginBean)) {
                return returnValidy(sn);
            }


            DataBean dataBean = loginService.getCreditLimit(sn);


            result.setData(dataBean);
            result.setDetail("Success");
            result.setSn(sn);
            result.setRst("1");

            return responseRR(result);


        } catch (Exception e) {
            log.info(e.getMessage());
            return returnFail(sn);
        }

    }

    @RequestMapping("logout")
    @ResponseBody
    public String logout(@RequestBody String s) {
        MakeBetBean request = null;
        String sn = null;
        String token = null;
        LoginBean loginBean;

        Result result = new Result();
        try {
            request = new GsonUtils().fromJson(s, MakeBetBean.class);
            sn = request.getSn();
            token = request.getToken();
            //check user
            loginBean = loginService.getMakeBean(sn, token);
            if (DataUtils.isEmpty(loginBean)) {
                return returnValidy(sn);
            }


            loginService.setToken(sn, "_");

            result.setDetail("Success");
            result.setSn(sn);
            result.setRst("1");

            return responseRR(result);


        } catch (Exception e) {
            log.info(e.getMessage());
            return returnFail(sn);
        }
    }


    @RequestMapping("changepassword")
    @ResponseBody
    public String changePassword(@RequestBody String s) {
        MakeBetBean request = null;
        String sn = null;
        String token = null;
        LoginBean loginBean;

        Result result = new Result();
        try {
            request = new GsonUtils().fromJson(s, MakeBetBean.class);
            sn = request.getSn();
            token = request.getToken();
            //check user
            loginBean = loginService.getMakeBean(sn, token);
            if (DataUtils.isEmpty(loginBean)) {
                return returnValidy(sn);
            }

            if (loginBean.getPassword().equals(request.getOld_password()))
            {
                loginService.changePassword(request.getNew_password(),sn);
                result.setDetail("Success");
                result.setSn(sn);
                result.setRst("1");

                return responseRR(result);
            }else {
                result.setDetail("Your credential does not match our record.");
                result.setSn(sn);
                result.setRst("1001");

                return responseRR(result);
            }


        } catch (Exception e) {
            log.info(e.getMessage());
            return returnFail(sn);
        }

    }


    @RequestMapping("deletebet")
    @ResponseBody
    public String deletebet(@RequestBody String s) {
        MakeBetBean request = null;
        String sn = null;
        String token = null;
        LoginBean loginBean;

        Result result = new Result();
        try {
            request = new GsonUtils().fromJson(s, MakeBetBean.class);
            sn = request.getSn();
            token = request.getToken();
            //check user
            loginBean = loginService.getMakeBean(sn, token);
            if (DataUtils.isEmpty(loginBean)) {
                return returnValidy(sn);
            }


            //查询记录
            DataBean data = makeBetService.getReprintData(sn);

            if (data ==null){
                result.setDetail("Last transaction does not exist.");
                result.setSn(sn);
                result.setRst("1001");

                return responseRR(result);
            }else {

                if (data.getStatus().equals("1")){

                    result.setDetail("No bet exists.");
                    result.setSn(sn);
                    result.setRst("1002");
                    return responseRR(result);

                }

                makeBetService.deleteMakeBet(data.getTx_id());

                result.setDetail( "Last transaction delete Success");
                result.setSn(sn);
                result.setRst("1");
                return responseRR(result);
            }


        }catch (Exception e){

            log.info(e.getMessage());
            return returnFail(sn);
        }
    }





}
