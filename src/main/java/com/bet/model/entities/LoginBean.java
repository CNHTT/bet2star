package com.bet.model.entities;

/**
 * Created by Extra on 2018/2/24.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
public class LoginBean {
    private String sn;
    private String password;
    private String default_type;
    private String default_sort;
    private String default_under;
    private double amount;


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
