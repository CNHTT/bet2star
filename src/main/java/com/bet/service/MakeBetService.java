package com.bet.service;

import com.bet.model.entities.*;

import java.util.List;

public interface MakeBetService {
    int selectCountTxid();

    boolean  addMakeBet(DataBean data, String sn);

    boolean  addBets(MakeBet bet,String sn,String tx_id);
    boolean  addBet(MakeBet bet,String sn,String tx_id);

    boolean  addGroupBean(String tx_id,String bet_bets_id,String group,String number,String item,String sn);

    DataBean getReprintData(String sn);

    List<MakeBet> getReprintMakeBet(String tx_id);

    List<GroupBean> getReprintGroupBeans(String bet_id);

    OddSummary getOddSummary(String sn, String s);

    DataBean getReportData(String sn);

    void deleteMakeBet(String tx_id);

    List<WinListBean> getWinList(String s, String week);

    String getWinTotal(String week, String sn);

    List<MakeBet> getWeek(String week);

    void updateAmount(MakeBet bet);

    void setWeek(String week, String s);

    void addWeek(String week, String s);
}
