package com.bet.model.entities;

import java.io.Serializable;

/**
 * Created by 戴尔 on 2018/2/1.
 */

public class OddSummary implements Serializable {

    /**
     * sort  : 1
     * count : 6
     * gross : 13400
     * payable : 9380
     * commission : 4020
     */

    private String sort;
    private String count;
    private String gross;
    private String payable;
    private String commission;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = gross;
    }

    public String getPayable() {
        return payable;
    }

    public void setPayable(String payable) {
        this.payable = payable;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }
}
