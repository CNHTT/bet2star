package com.bet.dao;

import com.bet.model.entities.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MakeBetDao {

    int selectCountTxid();

    int insertMakeBet(@Param("data")DataBean data, @Param("sn") String sn);

    int insertBet(@Param("bet")MakeBet bet,@Param("sn")String sn,@Param("tx_id")String tx_id);

    int insertBets(@Param("bet")MakeBet bet,@Param("sn")String sn,@Param("tx_id")String tx_id);

    int insertGroup(@Param("tx_id")String tx_id,@Param("bet_bets_id")String bet_bets_id, @Param("group") String group,@Param("number") String number,@Param("item") String item,@Param("sn") String sn);

    DataBean selectReprintData(String sn);

    List<MakeBet> selectReprintBets(String tx_id);

    List<GroupBean> selectReprintGroupBeans(String bet_id);

    OddSummary selectOddSumary(@Param("sn") String sn,@Param("s") String s);

    DataBean selectReportData(String sn);

    void deleteMakeBet(String tx_id);

    List<WinListBean> selectWinList(@Param("week") String week,@Param("sn") String sn);

    String selectWinTotal(@Param("week") String week,@Param("sn") String sn);
}
