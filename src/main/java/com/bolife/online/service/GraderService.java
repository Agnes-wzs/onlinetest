package com.bolife.online.service;

import java.util.List;
import java.util.Map;

import com.bolife.online.entity.Grade;

public interface GraderService {
    public Integer addGrade(Grade grade);

    Grade getGradeByConIdAndStuId(int contestId, int studentId);

    Map<String, Object> getGradesByStudentId(int pageNum, int pageSize, int studentId);

    List<Grade> getGradesByContestId(int contestId);

    boolean updateGrade(Grade grade);
}
