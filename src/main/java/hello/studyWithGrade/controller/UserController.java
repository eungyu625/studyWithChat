package hello.studyWithGrade.controller;

import hello.studyWithGrade.config.auth.LoginUser;
import hello.studyWithGrade.config.auth.dto.SessionUser;
import hello.studyWithGrade.dto.UserDto;
import hello.studyWithGrade.dto.myinfo.MyBoardDto;
import hello.studyWithGrade.dto.myinfo.MyCommentDto;
import hello.studyWithGrade.dto.myinfo.MyStudyDto;
import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.manytomany.StudyMember;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final StudyService studyService;
    private final StudyMemberService studyMemberService;

    @GetMapping("/userInfo/{userId}")
    public String userInformation(@PathVariable("userId") Long userId, Model model) {

        model.addAttribute("userDto", new UserDto(userService.findById(userId)));

        return "users/userInfo";
    }

    @GetMapping("/myInfo/board")
    public String myBoardInformation(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                     @LoginUser SessionUser sessionUser, Model model) {
        Page<Board> boardPage = boardService.findByUser(userService.findByEmail(sessionUser.getEmail()), pageable);
        Page<MyBoardDto> myBoardDtos = boardPage.map(board -> new MyBoardDto(board, commentService.findByBoard(board).size()));

        model.addAttribute("myBoardDtos", myBoardDtos);

        return "myInfo/boards";
    }

    @GetMapping("/myInfo/comment")
    public String myCommentInformation(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                       @LoginUser SessionUser sessionUser, Model model) {

        Page<Comment> commentPage = commentService.findByUser(userService.findByEmail(sessionUser.getEmail()), pageable);
        Page<MyCommentDto> myCommentDtos = commentPage.map(MyCommentDto::new);

        model.addAttribute("myCommentDtos", myCommentDtos);

        return "myInfo/comments";
    }

    @GetMapping("/myInfo/study")
    public String myStudyInformation(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                     @LoginUser SessionUser sessionUser, Model model) {

        Page<StudyMember> studyMembers = studyMemberService.findByUser(userService.findByEmail(sessionUser.getEmail()), pageable);
        Page<Study> studies = studyMembers.map(StudyMember::getStudy);

        model.addAttribute("myStudyDto", studies.map(MyStudyDto::new));

        return "myInfo/studies";
    }
}
