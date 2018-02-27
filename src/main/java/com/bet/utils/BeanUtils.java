package com.bet.utils;

import com.bet.model.response.ResponsePage;
import com.github.pagehelper.Page;

import java.util.ArrayList;

/**
 * Created by CT on 2017/7/15.
 */
public class BeanUtils {
    public  static <T> ResponsePage<T> toResponseResult(ArrayList<T> datas){
        ResponsePage<T> responsePage = new ResponsePage<T>();
        if (datas instanceof Page)
        {
            Page  page = (Page) datas;
            responsePage.setPageNo(page.getPageNum());
            responsePage.setPageSize(page.getPageSize());
            responsePage.setDataList(page.getResult());
            responsePage.setTotal(page.getTotal());
            responsePage.setPages(page.getPages());
        }else {
            responsePage.setPages(1);
            responsePage.setPageSize(datas.size());
            responsePage.setDataList(datas);
            responsePage.setTotal(datas.size());
        }

        return responsePage;
    }
}
