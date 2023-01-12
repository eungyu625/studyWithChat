package hello.studyWithGrade.controller;

import hello.studyWithGrade.config.auth.LoginUser;
import hello.studyWithGrade.config.auth.dto.SessionUser;
import hello.studyWithGrade.dto.BoardDto;
import hello.studyWithGrade.dto.form.BoardForm;
import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.service.BoardService;
import hello.studyWithGrade.service.CommentService;
import hello.studyWithGrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/board")
    public String writeForm(Model model) {

        model.addAttribute("boardForm", new BoardForm());

        return "boards/boardForm";
    }

    @PostMapping("/board")
    public String write(@Validated @ModelAttribute BoardForm boardForm, BindingResult result, @LoginUser SessionUser sessionUser) {

        User user = userService.findByEmail(sessionUser.getEmail());
        System.out.println(user);
        Board board = new Board();
        board.create(boardForm.getTitle(), boardForm.getContent(), user, List.of("new"));
        boardService.write(board);

        return "redirect:/";
    }

    @GetMapping("/board/{boardId}")
    public String getBoard(@PathVariable("boardId") Long boardId, Model model) {

        Board board = boardService.findById(boardId);
        List<Comment> comments = commentService.findByBoard(board);
        model.addAttribute("boardDto", new BoardDto(board, comments));

        return null;
    }
}
