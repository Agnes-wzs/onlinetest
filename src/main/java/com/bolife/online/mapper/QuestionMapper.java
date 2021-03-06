package com.bolife.online.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bolife.online.entity.Question;

@Mapper
public interface QuestionMapper {

    List<Question> getQuestionByContestId(@Param("contestId") Integer contestId);

    List<Question> getQuestionByPCD(@Param("problemsetId") Integer problemsetId, @Param("content") String content,
        @Param("difficulty") int difficulty);

    int getCountQuestionByPCD(@Param("problemsetId") Integer problemsetId, @Param("content") String content,
        @Param("difficulty") int difficulty);

    Question getQuestionById(@Param("id") Integer id);

    int getCountByContent(@Param("content") String content);

    List<Question> getQuestionsByContent(@Param("content") String content);

    int insertQuestion(@Param("question") Question question);

    Integer deleteQuestion(@Param("id") int id);

    Integer getQuestionCountByQuestionType(@Param("questionType") int questionType);

    List<Question> getAllQuestion();

    List<Question> getQuestionBySubjectId(@Param("subjectId") Integer subjectId);

    boolean updateQuestionById(@Param("question") Question question);

    Integer getCountBySubjectId(@Param("subjectId") Integer subjectId);
}
