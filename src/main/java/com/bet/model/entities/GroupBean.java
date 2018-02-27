package com.bet.model.entities;

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

    private String group;
    private String number;
    private String item;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
                "A:'" + group + '\'' +
                "  :" + number +
                "(" + item + ")\n" ;
    }
}
