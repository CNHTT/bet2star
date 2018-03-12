package com.bet.model.entities;

import java.util.List;

/**
 * Created by Extra on 2018/2/27.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
public class MakeBetBean{
    private String sn;

    private String token;

    private String week;

    private String  old_password  ;
    private String   new_password  ;


    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    private List<MakeBet> data ;
    public static  class Data<T> {
        private String id;

        private String type;

        private String sort;

        private String under;

        private String amount;

        private T games;

        public T getGames() {
            return games;
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

        public void setGames(T games) {
            this.games = games;
        }

     }


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<MakeBet> getData() {
        return data;
    }

    public void setData(List<MakeBet> data) {
        this.data = data;
    }
}
