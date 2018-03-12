package com.bet.model.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Extra on 2018/2/24.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
public class Result implements Serializable{
    private String sn;
    private String rst;
    private String token;
    private String detail;
    private DataBean data;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getRst() {
        return rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
    }
