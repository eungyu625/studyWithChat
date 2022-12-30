package hello.studyWithGrade.controller;

import hello.studyWithGrade.config.auth.LoginUser;
import hello.studyWithGrade.config.auth.dto.SessionUser;
import hello.studyWithGrade.dto.BoardDto;
import hello.studyWithGrade.dto.form.BoardForm;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.service.BoardService;
import hello.studyWithGrade.service.CommentService;
import hello.studyWithGrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/studyWithGrade/board")
    public ResponseEntity<BoardForm> getBoardForm() {

        return ResponseEntity.ok(new BoardForm());
    }

    @PostMapping("/studyWithGrade/board")
    public ResponseEntity<?> write(@RequestBody BoardForm boardForm, @LoginUser SessionUser sessionUser) {

        LocalDateTime now = LocalDateTime.now();
        User user = userService.findByEmail(sessionUser.getEmail());

        if (!check(now, user.getRecentBoardWriteTime())) {

            return null; // 예외 처리를 하는 값 반환
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    private boolean check(LocalDateTime now, LocalDateTime recent) {
        if (ChronoUnit.YEARS.between(now, recent) == 0 && ChronoUnit.MONTHS.between(now, recent) == 0
                && ChronoUnit.WEEKS.between(now, recent) == 0 && ChronoUnit.DAYS.between(now, recent) == 0
                && ChronoUnit.HOURS.between(now, recent) == 0 && ChronoUnit.MINUTES.between(now, recent) < 10) {
            return false;
        }
        return true;
    }
}
