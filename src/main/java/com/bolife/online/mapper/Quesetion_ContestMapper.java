package com.bolife.online.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bolife.online.entity.Question_Contest;

@Mapper
public interface Quesetion_ContestMapper {
    void insert(@Param("contestId") Integer contestId, @Param("questionId") int questionId);

    List<Question_Contest> getByContestId(@Param("contestId") Integer contestId);

    Boolean deleteQuestion(@Param("questionId") int questionId);
}
