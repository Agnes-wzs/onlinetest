package com.bolife.online.service;

import java.util.List;
import java.util.Map;

import com.bolife.online.entity.Contest;

public interface ContestService {
    public Map<String, Object> getContests(int pageNum, int pageSize);

    Contest getContestById(int contestId);

    List<Contest> getAllContests();

    boolean updateContest(Contest contest);

    boolean deleteContest(int id);

    Integer addContest(Contest contest);

    void updateContestStateById(int id, int contestState);

    void updataTotalScore(Integer contestId, Integer totalScore);
}
