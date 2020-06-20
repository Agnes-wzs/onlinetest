package com.bolife.online.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bolife.online.entity.Grade;

@Mapper
public interface GradeMapper {
    Integer insertGrade(@Param("grade") Grade grade);

    Grade getGradeByConIdAndStuId(@Param("contestId") Integer contestId, @Param("studentId") Integer studentId);

    int getCountByStudentId(int studentId);

    List<Grade> getGradesByStudentId(int studentId);

    List<Grade> getGradesByContestId(@Param("contestId") int contestId);

    Integer updateGrade(@Param("grade") Grade grade);
}
