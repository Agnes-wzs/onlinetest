package com.bolife.online.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Subject;
import com.bolife.online.service.SubjectService;
import com.bolife.online.util.FinalDefine;

@Controller
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    //添加课程
    @RequestMapping(value = "/api/addSubject", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addSubject(@RequestBody Subject subject) {
        AjaxResult ajaxResult = new AjaxResult();
        subject.setImgUrl(FinalDefine.DEFAULT_SUBJECT_IMG_URL);
        subject.setQuestionNum(0);
        subject.setUpdateTime(new Date());
        int subjectId = subjectService.addSubject(subject);
        return new AjaxResult().setData(subjectId);
    }

    //更新课程
    @RequestMapping(value = "/api/updateSubject", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateSubject(@RequestBody Subject subject) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = subjectService.updateSubject(subject);
        return new AjaxResult().setData(result);
    }

    //删除课程
    @RequestMapping("/api/deleteSubject/{id}")
    @ResponseBody
    public AjaxResult deleteSubject(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = subjectService.deleteSubjectById(id);
        return new AjaxResult().setData(result);
    }
}
