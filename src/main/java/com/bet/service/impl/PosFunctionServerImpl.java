package com.bet.service.impl;

import com.bet.dao.PosFunctionDao;
import com.bet.model.entities.DataBean;
import com.bet.service.PosFunctionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class PosFunctionServerImpl implements PosFunctionService {

    @Resource
    private PosFunctionDao posFunctionDao;

    @Override
    public String findWeekResult(String week) {
        return posFunctionDao.selectWeekResult(week);
    }
}
