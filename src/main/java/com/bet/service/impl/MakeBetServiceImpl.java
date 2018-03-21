package com.bet.service.impl;

import com.bet.dao.MakeBetDao;
import com.bet.model.entities.*;
import com.bet.service.MakeBetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MakeBetServiceImpl implements MakeBetService {


    @Resource
    private MakeBetDao makeBetDao;

    @Override
    public int selectCountTxid() {
        return makeBetDao.selectCountTxid();
    }

    @Override
    public boolean addMakeBet(DataBean data, String sn) {
        return makeBetDao.insertMakeBet(data,sn)!=0;
    }

    @Override
    public boolean addBets(MakeBet bet, String sn, String tx_id) {
        return makeBetDao.insertBets(bet, sn, tx_id)!=0;
    }

    @Override
    public boolean addBet(MakeBet bet, String sn, String tx_id) {
        return makeBetDao.insertBet(bet, sn, tx_id)!=0;
    }

    @Override
    public boolean addGroupBean(String tx_id,String bet_bets_id,String group,String number,String item,String bet_d){
        return makeBetDao.insertGroup(tx_id,bet_bets_id,group, number, item, bet_d)!=0;
    }

    @Override
    public DataBean getReprintData(String sn) {
        return makeBetDao.selectReprintData(sn);
    }

    @Override
    public List<MakeBet> getReprintMakeBet(String tx_id) {
        return makeBetDao.selectReprintBets(tx_id);
    }

    @Override
    public List<GroupBean> getReprintGroupBeans(String bet_id) {
        return makeBetDao.selectReprintGroupBeans(bet_id);
    }

    @Override
    public OddSummary getOddSummary(String sn, String s) {
        return makeBetDao.selectOddSumary(sn,s);
    }

    @Override
    public DataBean getReportData(String sn) {
        return makeBetDao.selectReportData(sn);
    }

    @Override
    public void deleteMakeBet(String tx_id) {
        makeBetDao.deleteMakeBet(tx_id);
    }

    @Override
    public List<WinListBean> getWinList(String week, String sn) {
        return makeBetDao.selectWinList(week,sn);
    }

    @Override
    public String getWinTotal(String week, String sn) {
        return makeBetDao.selectWinTotal(week,sn);
    }

    @Override
    public List<MakeBet> getWeek(String week) {
        return makeBetDao.getWeek(week);
    }

    @Override
    public void updateAmount(MakeBet bet) {
        makeBetDao.updateBetAmount(bet);
    }

    @Override
    public void setWeek(String week, String s) {
        makeBetDao.selectWeek(week,s);
    }

    @Override
    public void addWeek(String week, String s) {
        makeBetDao.addWeek(week,s);
    }


}
