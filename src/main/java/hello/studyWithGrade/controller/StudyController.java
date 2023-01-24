package hello.studyWithGrade.controller;

import hello.studyWithGrade.config.auth.LoginUser;
import hello.studyWithGrade.config.auth.dto.SessionUser;
import hello.studyWithGrade.dto.StudyDto;
import hello.studyWithGrade.dto.StudyMemberDto;
import hello.studyWithGrade.dto.UserDto;
import hello.studyWithGrade.dto.myinfo.MyDto;
import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.manytomany.StudyMember;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyController {

    private final UserService userService;
    private final BoardService boardService;
    private final StudyService studyService;
    private final StudyMemberService studyMemberService;

    @GetMapping("/userInfo/forStudy/{boardId}/{userId}")
    public String applicantInformation(@PathVariable("boardId") Long boardId, @PathVariable("userId") Long userId, @LoginUser SessionUser sessionUser,
                                       @RequestParam(value = "선발", required = false) String pick, Model model) {

        User user = userService.findById(userId);

        if (StringUtils.hasText(pick)) {
            studyService.addMember(boardService.findById(boardId).getStudy(), user);

            model.addAttribute("userDto", new UserDto(user));
            model.addAttribute("isMember", true);

            return "users/forStudy/userInfo";
        }

        model.addAttribute("userDto", new UserDto(user));

        boolean isMember = false;

        for (StudyMember studyMember : boardService.findById(boardId).getStudy().getStudyMembers()) {
            if (studyMember.getUser() == user) {
                isMember = true;
                break;
            }
        }

        model.addAttribute("userDto", new UserDto(user));
        model.addAttribute("isMember", isMember);

        return "users/forStudy/userInfo";
    }

    @GetMapping("/study/{studyId}")
    public String studyInformation(@PathVariable("studyId") Long studyId, Model model, @LoginUser SessionUser sessionUser) {

        Study study = studyService.findById(studyId);
        User user = userService.findByEmail(sessionUser.getEmail());
        StudyMember studyMember = studyMemberService.findByUserAndStudy(user, study);
        List<User> studyMembers = userService.findStudyMembersByStudy(study);
        List<User> notEstimatedMembers = new ArrayList<>();
        List<User> estimatedMembers = new ArrayList<>();

        for (User member : studyMembers) {
            if (studyMember.getEstimatedMembers().contains(member.getId())) {
                estimatedMembers.add(member);
            } else {
                notEstimatedMembers.add(member);
            }
        }

        model.addAttribute("studyDto", new StudyDto(study, notEstimatedMembers, estimatedMembers));

        if (!study.isProgress()) {
            return "studies/study";
        }

        model.addAttribute("myDto", new MyDto(user.getId(), user.getEmail()));


        return "studies/finishedStudy";
    }

    @GetMapping("/myStudy/{myStudyId}")
    public String myStudyInformation(@PathVariable("myStudyId") Long myStudyId, Model model, @LoginUser SessionUser sessionUser,
                                     @RequestParam(value = "진행완료", required = false) String end) {

        if (StringUtils.hasText(end)) {
            userService.finish_study(userService.findByEmail(sessionUser.getEmail()));
            studyService.finish(studyService.findById(myStudyId));
        }

        Study study = studyService.findById(myStudyId);
        User user = userService.findByEmail(sessionUser.getEmail());
        StudyMember studyMember = studyMemberService.findByUserAndStudy(user, study);
        List<User> studyMembers = userService.findStudyMembersByStudy(study);
        List<User> notEstimatedMembers = new ArrayList<>();
        List<User> estimatedMembers = new ArrayList<>();

        for (User member : studyMembers) {
            if (studyMember.getEstimatedMembers().contains(member.getId())) {
                estimatedMembers.add(member);
            } else {
                notEstimatedMembers.add(member);
            }
        }

        model.addAttribute("studyDto", new StudyDto(study, notEstimatedMembers, estimatedMembers));

        if (!study.isStart()) {
            return "studies/preparingStudy";
        }

        if (!study.isProgress()) {
            return "studies/myStudy";
        }

        model.addAttribute("myDto", new MyDto(user.getId(), user.getEmail()));

        return "studies/finishedStudy";
    }

    @GetMapping("/estimate/{studyId}/{studyMemberId}")
    public String estimateStudyMember(@PathVariable("studyId") Long studyId, @LoginUser SessionUser sessionUser,
                                      @PathVariable("studyMemberId") Long studyMemberId, @RequestParam(value = "평점", required = false) String grade,
                                      Model model) {

        if (studyMemberService.findByUserAndStudy(userService.findByEmail(sessionUser.getEmail()), studyService.findById(studyId)).getEstimatedMembers().contains(studyMemberId)) {
            return "studies/estimated";
        }

        if (StringUtils.hasText(grade)) {
            double new_grade = calculate(grade);
            User user = userService.findByEmail(sessionUser.getEmail()); // 평가한 사람(현재 사용자)
            Study study = studyService.findById(studyId);

            User studyMember = userService.findById(studyMemberId); // 평가받는 사람
            userService.estimate(studyMember, new_grade); // 평가받는 사람 점수 저장
            studyMemberService.estimated(studyMemberService.findByUserAndStudy(user, study), studyMember);

            return "studies/estimated";
        }

        User member = userService.findById(studyMemberId);
        model.addAttribute("userDto", new StudyMemberDto(member.getId(), member.getEmail()));

        return "studies/estimateForm";
    }

    private double calculate(String grade) {
        double new_grade = 0.;

        for (int i = 0; i < grade.length(); i++) {
            new_grade += (double) grade.charAt(i) - 48;
        }

        return new_grade;
    }
}
