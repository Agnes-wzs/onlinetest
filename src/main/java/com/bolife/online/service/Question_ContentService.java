package com.bolife.online.service;

import java.util.List;

import com.bolife.online.entity.Question;
import com.bolife.online.entity.Question_Contest;

public interface Question_ContentService {
    Integer saveQuestion(Integer contestId, List<Question> queTypes);

    List<Question_Contest> getByContestId(Integer contestId);

    Boolean deleteQuestion(int id);
}
