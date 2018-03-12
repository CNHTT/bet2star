package com.bet.utils;

import com.bet.model.entities.GroupBean;
import com.google.gson.internal.LinkedTreeMap;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Extra on 2018/2/9.
 * GitHub cnhttt@163.com
 * Work to SZFP
 * 购买
 */
public class CalculateUtils {
     public static  DecimalFormat    df   = new DecimalFormat("######0.00");

    public static long calculate(String u, int length){
        int intU = Integer.parseInt(u);
        long a =length;
        for (int i = 1; i <intU ; i++) {
            if ((length-(intU-i))==0)a*=intU;else
            a = a *(length-(intU-i));
        }
        long num = 1;
        for (int i = 1; i <= intU; i++) {
            num *= i;
        }
        return a/num;
    }


    public static String calculateNapOrPerm(String under,int length,String amount){
        double amountDouble = Double.parseDouble(amount);
        return df.format(amountDouble/calculate(under,length));
    }

    public static String  calculateCombination(String unders , int lengh, String amount){
        long line=0;
        String [] lines = unders.split(",");
        for (int i = 0; i <lines.length ; i++) {
            line+=calculate(lines[i],lengh);
        }
        double amountDouble = Double.parseDouble(amount);
        System.out.println(line);
        return df.format(amountDouble/line);
    }

    public static String calculateGrouping( List<LinkedTreeMap<String,String>>  list, String amount){

//        for (LinkedTreeMap<String,String> g:groupBeans) {
//            canvas.drawText(g.get("group")+": "+g.get("number")+"("+g.get("item")+")", paint);
//        }
        int line = 1;
        for (int i = 0; i <list.size() ; i++) {
            LinkedTreeMap<String,String> groupBean = list.get(i);
            line= (int) (line*calculate(groupBean.get("number"),groupBean.get("item").split(",").length));
        }
        double amountDouble = Double.parseDouble(amount);
//        System.out.println(line);
        return df.format(amountDouble/line);

    }

    public static String setBetId(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSS");
        String  formDate =sdf.format(date);
        System.out.println(formDate);
        return  formDate.substring(2);
    }
}
