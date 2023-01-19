package hello.studyWithGrade.controller;

import hello.studyWithGrade.config.auth.LoginUser;
import hello.studyWithGrade.config.auth.dto.SessionUser;
import hello.studyWithGrade.dto.BoardDto;
import hello.studyWithGrade.dto.UserDto;
import hello.studyWithGrade.dto.form.BoardForm;
import hello.studyWithGrade.dto.form.CommentForm;
import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.service.BoardService;
import hello.studyWithGrade.service.CommentService;
import hello.studyWithGrade.service.StudyService;
import hello.studyWithGrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final StudyService studyService;

    @GetMapping("/board")
    public String writeForm(Model model) {

        model.addAttribute("boardForm", new BoardForm());

        return "boards/boardForm";
    }

    @PostMapping("/board")
    public String write(@Validated @ModelAttribute BoardForm boardForm, BindingResult result, @LoginUser SessionUser sessionUser) {

        if (!StringUtils.hasText(boardForm.getTitle())) {
            result.rejectValue("title", "required", null, null);
        }

        if (!StringUtils.hasText(boardForm.getContent())) {
            result.rejectValue("content", "required", null, null);
        }

        if (boardForm.getKeyword().size() == 0) {
            result.rejectValue("keyword", "required", null, null);
        }

        if (!StringUtils.hasText(boardForm.getStudyName())) {
            result.rejectValue("studyName", "required", null, null);
        }

        if (result.hasErrors()) {
            return "boards/boardForm";
        }

        User user = userService.findByEmail(sessionUser.getEmail());
        Board board = new Board();
        Study study = new Study();
        study.create(boardForm.getStudyName(), user);
        studyService.create(study);
        board.create(boardForm.getTitle(), boardForm.getContent(), user, boardForm.getKeyword(), study);
        boardService.write(board);

        return "redirect:/";
    }

    @GetMapping("/board/{boardId}")
    public String getBoard(@PathVariable("boardId") Long boardId, Model model, @LoginUser SessionUser sessionUser, @RequestParam(value = "모집완료", required = false) String progress) {

        if (StringUtils.hasText(progress)) {
            boardService.finishRecruiting(boardService.findById(boardId));
            studyService.start(boardService.findById(boardId).getStudy());
            return "redirect:/board/" + boardId;
        }

        Board board = boardService.findById(boardId);
        List<Comment> comments = commentService.findByBoard(board);
        model.addAttribute("boardDto", new BoardDto(board, comments));
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("userDto", new UserDto(userService.findByEmail(sessionUser.getEmail())));

        return "boards/board";
    }

    @PostMapping("/board/{boardId}")
    public String recruiting(@Validated @ModelAttribute CommentForm commentForm, BindingResult result,
                             @PathVariable("boardId") Long boardId, Model model, @LoginUser SessionUser sessionUser) {

        if (!StringUtils.hasText(commentForm.getContent())) {
            result.rejectValue("content", "required", null, null);
        }

        if (result.hasErrors()) {
            model.addAttribute("boardDto", new BoardDto(boardService.findById(boardId), commentService.findByBoard(boardService.findById(boardId))));
            model.addAttribute("userDto", new UserDto(userService.findByEmail(sessionUser.getEmail())));
            return "boards/board";
        }

        Board board = boardService.findById(boardId);
        User user = userService.findByEmail(sessionUser.getEmail());
        Comment comment = new Comment();
        comment.create(commentForm.getContent(), board, user);
        commentService.write(comment);

        return "redirect:/board/" + boardId;
    }
}
