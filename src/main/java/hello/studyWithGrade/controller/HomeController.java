package hello.studyWithGrade.controller;

import hello.studyWithGrade.dto.BoardDto;
import hello.studyWithGrade.dto.MainDto;
import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.service.BoardService;
import hello.studyWithGrade.service.CommentService;
import hello.studyWithGrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<List<MainDto>> home(@RequestParam(value = "title", required = false) String title) {
        List<MainDto> mainDtos = new ArrayList<>();
        if (title == null || title.length() == 0) {
            for (Board board : boardService.findAll()) {
                Long id = board.getId();
                String boardTitle = board.getTitle();
                String username = board.getUser().getEmail();
                LocalDateTime writeTime = board.getWriteTime();
                Integer commentNumber = commentService.findByBoard(board).size();
                mainDtos.add(new MainDto(id, boardTitle, username, writeTime, commentNumber));
            }
        } else {
            for (Board board : boardService.findByTitleLike(title)) {
                Long id = board.getId();
                String boardTitle = board.getTitle();
                String username = board.getUser().getEmail();
                LocalDateTime writeTime = board.getWriteTime();
                Integer commentNumber = commentService.findByBoard(board).size();
                mainDtos.add(new MainDto(id, boardTitle, username, writeTime, commentNumber));
            }
        }

        return ResponseEntity.ok(mainDtos);
    }
}
