package com.bet.model.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 戴尔 on 2018/1/24.
 */

public class GroupBean implements Serializable {
    /**
     * group : A
     * number : 2
     * item : 1,2,3,4
     */

    @SerializedName("group")
    private String bet_group;
    private String number;
    private String item;

    public String getGroup() {
        return bet_group;
    }

    public void setGroup(String group) {
        this.bet_group = group;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "" +
                "" +
                "A:'" + bet_group + '\'' +
                "  :" + number +
                "(" + item + ")\n" ;
    }
}
