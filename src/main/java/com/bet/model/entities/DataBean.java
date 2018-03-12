package com.bet.model.entities;

import java.util.List;

public class DataBean {


    private String sn;
    private String datetime;


    /**
     * default_type : 1
     * default_sort : 3
     * default_under : 4
     */


    private String default_type;
    private String default_sort;
    private String default_under;
    private String week;
    private List<String> drawn;

    private String played_date;
    private String close_date;
    private String validity;
    private String total_stake;
    private String tx_id;

    private List<MakeBet> bets;


    private String agent;
    private String operator;
    private String total;
    private List<WinListBean> winlist;


    //
    private String total_payable;
    private String total_winning;
    private String balance_comp;
    private String balance_agen;
    private String status;
    private List<OddSummary> odd_summary;

    private String credit_limit;


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDefault_type() {
        return default_type;
    }

    public void setDefault_type(String default_type) {
        this.default_type = default_type;
    }

    public String getDefault_sort() {
        return default_sort;
    }

    public void setDefault_sort(String default_sort) {
        this.default_sort = default_sort;
    }

    public String getDefault_under() {
        return default_under;
    }

    public void setDefault_under(String default_under) {
        this.default_under = default_under;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public List<String> getDrawn() {
        return drawn;
    }

    public void setDrawn(List<String> drawn) {
        this.drawn = drawn;
    }

    public String getPlayed_date() {
        return played_date;
    }

    public void setPlayed_date(String played_date) {
        this.played_date = played_date;
    }

    public String getClose_date() {
        return close_date;
    }

    public void setClose_date(String close_date) {
        this.close_date = close_date;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getTotal_stake() {
        return total_stake;
    }

    public void setTotal_stake(String total_stake) {
        this.total_stake = total_stake;
    }

    public String getTx_id() {
        return tx_id;
    }

    public void setTx_id(String tx_id) {
        this.tx_id = tx_id;
    }

    public List<MakeBet> getBets() {
        return bets;
    }

    public void setBets(List<MakeBet> bets) {
        this.bets = bets;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<WinListBean> getWinlist() {
        return winlist;
    }

    public void setWinlist(List<WinListBean> winlist) {
        this.winlist = winlist;
    }

    public String getTotal_payable() {
        return total_payable;
    }

    public void setTotal_payable(String total_payable) {
        this.total_payable = total_payable;
    }

    public String getTotal_winning() {
        return total_winning;
    }

    public void setTotal_winning(String total_winning) {
        this.total_winning = total_winning;
    }

    public String getBalance_comp() {
        return balance_comp;
    }

    public void setBalance_comp(String balance_comp) {
        this.balance_comp = balance_comp;
    }

    public String getBalance_agen() {
        return balance_agen;
    }

    public void setBalance_agen(String balance_agen) {
        this.balance_agen = balance_agen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OddSummary> getOdd_summary() {
        return odd_summary;
    }

    public void setOdd_summary(List<OddSummary> odd_summary) {
        this.odd_summary = odd_summary;
    }

    public String getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(String credit_limit) {
        this.credit_limit = credit_limit;
    }
}
