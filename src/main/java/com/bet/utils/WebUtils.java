package com.bet.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Extra on 2017/7/24.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
public class WebUtils {
    private WebUtils(){}

    /**
     * 判断是否为ajax请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request){
        boolean flag = false;
        String requestType = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(requestType)){
            flag = true;
        }
        return flag;
    }
}
