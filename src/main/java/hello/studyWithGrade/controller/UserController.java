package hello.studyWithGrade.controller;

import hello.studyWithGrade.config.auth.LoginUser;
import hello.studyWithGrade.config.auth.dto.SessionUser;
import hello.studyWithGrade.dto.myinfo.MyBoardDto;
import hello.studyWithGrade.dto.myinfo.MyCommentDto;
import hello.studyWithGrade.dto.myinfo.MyStudyDto;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.service.BoardService;
import hello.studyWithGrade.service.CommentService;
import hello.studyWithGrade.service.StudyService;
import hello.studyWithGrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final StudyService studyService;

    @GetMapping("/studyWithGrade/myInfo/board")
    public ResponseEntity<List<MyBoardDto>> getMyBoards(@LoginUser SessionUser sessionUser) {

        User user = userService.findByEmail(sessionUser.getEmail());

        List<MyBoardDto> myBoardDtos = boardService.findByUser(user).stream().map(MyBoardDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(myBoardDtos);
    }

    @GetMapping("/studyWithGrade/myInfo/comment")
    public ResponseEntity<List<MyCommentDto>> getMyComments(@LoginUser SessionUser sessionUser) {

        User user = userService.findByEmail(sessionUser.getEmail());

        List<MyCommentDto> myCommentDtos =  commentService.findByUser(user).stream().map(MyCommentDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(myCommentDtos);
    }

    @GetMapping("/studyWithGrade/myInfo/study")
    public ResponseEntity<List<MyStudyDto>> getMyStudies(@LoginUser SessionUser sessionUser) {

        User user = userService.findByEmail(sessionUser.getEmail());

        List<MyStudyDto> myStudyDtos = studyService.findByUser(user).stream().map(MyStudyDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(myStudyDtos);
    }
}
