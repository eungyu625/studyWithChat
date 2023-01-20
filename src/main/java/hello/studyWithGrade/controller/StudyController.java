package hello.studyWithGrade.controller;

import hello.studyWithGrade.config.auth.LoginUser;
import hello.studyWithGrade.config.auth.dto.SessionUser;
import hello.studyWithGrade.dto.StudyDto;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;
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
    public String studyInformation(@PathVariable("studyId") Long studyId, Model model) {

        Study study = studyService.findById(studyId);
        List<User> users = userService.findStudyMembersByStudy(study);
        StudyDto studyDto = new StudyDto(study, users);

        model.addAttribute("studyDto", studyDto);

        if (!study.isProgress()) {
            return "studies/study";
        }

        return "studies/finishedStudy";
    }

    @GetMapping("/myStudy/{myStudyId}")
    public String myStudyInformation(@PathVariable("myStudyId") Long myStudyId, Model model, @LoginUser SessionUser sessionUser,
                                     @RequestParam(value = "진행완료", required = false) String end) {

        if (StringUtils.hasText(end)) {
            studyService.finish(studyService.findById(myStudyId));
        }

        User user = userService.findByEmail(sessionUser.getEmail());

        Study study = studyService.findById(myStudyId);
        StudyMember studyMember = studyMemberService.findByUserAndStudy(user, study);
        List<User> users = userService.findStudyMembersByStudy(study);
        StudyDto studyDto = new StudyDto(study, users);

        model.addAttribute("studyDto", studyDto);
        model.addAttribute("myStudyMemberDto", new MyDto(user.getId(), user.getEmail()));

        if (!study.isProgress()) {
            return "studies/myStudy";
        }

        return "studies/finishedStudy";
    }

    @GetMapping("/estimate/{studyId}/{studyMemberId}")
    public String estimateStudyMember(@PathVariable("studyId") Long studyId, @LoginUser SessionUser sessionUser,
                                      @PathVariable("studyMemberId") Long studyMemberId, @RequestParam("평점") String grade) {

        return "studies/estimateForm";
    }
}
