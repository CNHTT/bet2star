package com.bet.model.entities;

import java.io.Serializable;

/**
 * Created by 戴尔 on 2018/1/31.
 */

public class WinListBean implements Serializable {


    /**
     * bet_id : 123126
     * amount : 5000
     */

    private String bet_id;
    private String amount;

    public String getBet_id() {
        return bet_id;
    }

    public void setBet_id(String bet_id) {
        this.bet_id = bet_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
