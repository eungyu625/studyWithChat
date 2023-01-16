package hello.studyWithGrade.controller;

import hello.studyWithGrade.dto.UserDto;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.service.BoardService;
import hello.studyWithGrade.service.StudyService;
import hello.studyWithGrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class StudyController {

    private final UserService userService;
    private final BoardService boardService;
    private final StudyService studyService;

    @GetMapping("/userInfo/forStudy/{boardId}/{userId}")
    public String applicantInformation(@PathVariable("boardId") Long boardId, @PathVariable("userId") Long userId,
                                       @RequestParam(value = "선발", required = false) String pick, Model model) {

        User user = userService.findById(userId);

        if (StringUtils.hasText(pick)) {
            studyService.addMember(boardService.findById(boardId).getStudy(), user);

            return "users/forStudy/userInfo";
        }

        model.addAttribute("userDto", new UserDto(user));

        return "users/forStudy/userInfo";
    }
}
