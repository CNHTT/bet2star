package com.bet.dao;

import com.bet.model.entities.DataBean;
import org.springframework.stereotype.Repository;

@Repository
public interface PosFunctionDao {
    String selectWeekResult(String week);
}
