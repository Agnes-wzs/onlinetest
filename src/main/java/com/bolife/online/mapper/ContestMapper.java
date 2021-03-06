package com.bolife.online.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bolife.online.entity.Contest;

@Mapper
public interface ContestMapper {
    /***
     * 获取个数
     * @return
     */
    public Integer getCount();

    /***
     * 获取全部的试题
     * @return
     */
    public List<Contest> getContests();

    Contest getContestById(@Param("contestId") int contestId);

    Integer updateContestById(@Param("contest") Contest contest);

    Integer deleteContest(@Param("id") int id);

    Integer insertContest(@Param("contest") Contest contest);

    void updateContestStateById(@Param("id") int id, @Param("contestState") int contestState);

    void updateTotalScore(@Param("contestId") Integer contestId, @Param("totalScore") Integer totalScore);
}
