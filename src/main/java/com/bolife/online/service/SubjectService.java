package com.bolife.online.service;

import java.util.List;
import java.util.Map;

import com.bolife.online.entity.Subject;

public interface SubjectService {
    public Map<String, Object> getSubjects(int pageNum, int pageSize);

    Subject getSubjectById(Integer problemsetId);

    List<Subject> getAllSubjects();

    int addSubject(Subject subject);

    boolean updateSubject(Subject subject);

    boolean deleteSubjectById(int id);
}
