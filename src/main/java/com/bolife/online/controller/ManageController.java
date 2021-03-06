package com.bolife.online.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolife.online.entity.Account;
import com.bolife.online.entity.Contest;
import com.bolife.online.entity.Grade;
import com.bolife.online.entity.Question;
import com.bolife.online.entity.Question_Contest;
import com.bolife.online.entity.Subject;
import com.bolife.online.service.AccountService;
import com.bolife.online.service.ContestService;
import com.bolife.online.service.GraderService;
import com.bolife.online.service.QuestionService;
import com.bolife.online.service.Question_ContentService;
import com.bolife.online.service.SubjectService;
import com.bolife.online.util.FinalDefine;

@Controller
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    private ContestService contestService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private GraderService graderService;

    @Autowired
    private Question_ContentService question_contentService;

    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        Account currentAccount = (Account)request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null) {
            return "/manage/manage-login";
        } else {
            return "redirect:/manage/contest/list";
        }
    }

    @RequestMapping(value = "/contest/list", method = RequestMethod.GET)
    public String contestList(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page,
        Model model) {
        Account currentAccount = (Account)request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::处理
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        Map<String, Object> data = contestService.getContests(page, FinalDefine.contestPageSize);
        List<Contest> contestList = (List<Contest>)data.get("contests");
        for (Contest contest : contestList) {
            Date startTime = contest.getStartTime();
            long time = startTime.getTime();
            long endDate = contest.getEndTime().getTime();
            long nowDate = new Date().getTime();
            if (time > nowDate) {
                contestService.updateContestStateById(contest.getId(), 0);
                contest.setState(0);
            }
            if (time <= nowDate && nowDate < endDate) {
                contestService.updateContestStateById(contest.getId(), 1);
                contest.setState(1);
            }
            if (endDate <= nowDate && contest.getState() == 1) {
                contestService.updateContestStateById(contest.getId(), 2);
                contest.setState(2);
            }
        }
        data.put("contests", contestList);
        List<Subject> subjects = subjectService.getAllSubjects();
        data.put("subjects", subjects);
        model.addAttribute(FinalDefine.DATA, data);
        return "/manage/manage-contestBoard";
    }

    @RequestMapping(value = "/contest/{contestId}/problems", method = RequestMethod.GET)
    public String contestProblemList(HttpServletRequest request, @PathVariable("contestId") Integer contestId,
        Model model) {
        Account currentAccount = (Account)request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        Map<String, Object> data = new HashMap<>();
        List<Question_Contest> byContestId = question_contentService.getByContestId(contestId);
        List<Question> questions = questionService.getQuestionByIds(byContestId);
        Contest contest = contestService.getContestById(contestId);
        data.put("questionsSize", questions.size());
        data.put("questions", questions);
        data.put("contest", contest);
        model.addAttribute(FinalDefine.DATA, data);
        return "/manage/manage-editContestProblem";
    }

    @RequestMapping(value = "/result/contest/list", method = RequestMethod.GET)
    public String resultContestList(HttpServletRequest request,
        @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Account currentAccount = (Account)request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        Map<String, Object> data = contestService.getContests(page, FinalDefine.contestPageSize);
        List<Subject> subjects = subjectService.getAllSubjects();
        data.put("subjects", subjects);
        model.addAttribute(FinalDefine.DATA, data);
        return "/manage/manage-resultContestBoard";
    }

    @RequestMapping(value = "/result/contest/{contestId}/list", method = RequestMethod.GET)
    public String resultStudentList(HttpServletRequest request, @PathVariable("contestId") int contestId,
        @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Account currentAccount = (Account)request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::处理
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        Map<String, Object> data = new HashMap<>();
        List<Grade> grades = graderService.getGradesByContestId(contestId);
        Contest contest = contestService.getContestById(contestId);
        List<Question_Contest> byContestId = question_contentService.getByContestId(contestId);
        List<Question> questions = questionService.getQuestionByIds(byContestId);
        List<Account> students = accountService.getAllAccount();
        Map<Integer, Account> id2student =
            students.stream().collect(Collectors.toMap(Account::getId, account -> account));
        for (Grade grade : grades) {
            Account student = id2student.get(grade.getStudentId());
            grade.setStudent(student);
        }
        data.put("grades", grades);
        data.put("contest", contest);
        data.put("questions", questions);
        model.addAttribute(FinalDefine.DATA, data);
        return "/manage/manage-resultStudentBoard";
    }

    @RequestMapping(value = "/question/list", method = RequestMethod.GET)
    public String questionList(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "content", defaultValue = "") String content, Model model) {
        Account currentAccount = (Account)request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::处理
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        Map<String, Object> data = questionService.getQuestionsByContent(page, FinalDefine.questionPageSize, content);
        List<Question> questions = (List<Question>)data.get("questions");
        List<Subject> subjects = subjectService.getAllSubjects();
        Map<Integer, String> subjectId2name =
            subjects.stream().collect(Collectors.toMap(Subject::getId, Subject::getName));
        for (Question question : questions) {
            question.setSubjectName(subjectId2name.getOrDefault(question.getSubjectId(), "未知科目"));
        }
        data.put("subjects", subjects);
        data.put("content", content);
        model.addAttribute("data", data);
        return "/manage/manage-questionBoard";
    }

    @RequestMapping(value = "/subject/list", method = RequestMethod.GET)
    public String subjectList(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page,
        Model model) {
        Account currentAccount = (Account)request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        Map<String, Object> data = subjectService.getSubjects(page, FinalDefine.subjectPageSize);
        List<Subject> subjects = (List<Subject>)data.get("subjects");
        for (Subject subject : subjects) {
            Integer countQuestionBySubject = questionService.getCountQuestionBySubject(subject.getId());
            subject.setQuestionNum(countQuestionBySubject);
        }
        model.addAttribute(FinalDefine.DATA, data);
        return "/manage/manage-subjectBoard";
    }

    @RequestMapping(value = "/account/list", method = RequestMethod.GET)
    public String accountList(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page,
        Model model) {
        Account currentAccount = (Account)request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        Map<String, Object> data = accountService.getAccounts(page, FinalDefine.accountPageSize);
        model.addAttribute(FinalDefine.DATA, data);
        return "/manage/manage-accountList";
    }
}
