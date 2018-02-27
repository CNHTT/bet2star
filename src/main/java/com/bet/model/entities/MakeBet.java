package com.bet.model.entities;

import java.io.Serializable;

/**
 * Created by 戴尔 on 2018/1/23.
 */

public class MakeBet<T> implements Serializable {

    /**
     * id : 1
     * type : 1
     * sort : 1
     * under : 3
     * amount : 1000
     * games : 1,2,3,4
     */

    private String id;
    private String type;
    private String sort;
    private String under;
    private String amount;
    private T games;
    private String apl;

    private String status;
    private String description;

    private String bet_id;


    public String getApl() {
        return apl;
    }

    public void setApl(String apl) {
        this.apl = apl;
    }

    public String getBet_id() {
        return bet_id;
    }

    public void setBet_id(String bet_id) {
        this.bet_id = bet_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getUnder() {
        return under;
    }

    public void setUnder(String under) {
        this.under = under;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public T getGames() {
        return games;
    }

    public void setGames(T games) {
        this.games = games;
    }
}
