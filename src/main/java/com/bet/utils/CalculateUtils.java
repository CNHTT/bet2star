package com.bet.utils;

import com.bet.model.entities.GroupBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Extra on 2018/2/9.
 * GitHub cnhttt@163.com
 * Work to SZFP
 * 购买
 */
public class CalculateUtils {
     static  DecimalFormat    df   = new DecimalFormat("######0.00");

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


    public static void main(String[] args) {
        System.out.println(calculateCombination("4,5,6",6,"208"));
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

    public static String calculateGrouping(List<GroupBean> list,String amount){
        int line = 1;
        for (int i = 0; i <list.size() ; i++) {
            GroupBean groupBean = list.get(i);
            line= (int) (line*calculate(groupBean.getNumber(),groupBean.getItem().split(",").length));
        }
        double amountDouble = Double.parseDouble(amount);
        System.out.println(line);
        return df.format(amountDouble/line);

    };
}
