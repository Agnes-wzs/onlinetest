package com.bolife.online.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bolife.online.entity.Subject;

@Mapper
public interface SubjectMapper {
    public List<Subject> getSubjects();

    Integer getCount();

    Subject getSubjectById(@Param("id") Integer id);

    int insertSubject(@Param("subject") Subject subject);

    Integer updateSubject(@Param("subject") Subject subject);

    boolean deleteSubjectById(@Param("id") int id);
}
