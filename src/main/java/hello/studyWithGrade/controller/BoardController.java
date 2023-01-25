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
import hello.studyWithGrade.entity.manytomany.StudyMember;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.service.BoardService;
import hello.studyWithGrade.service.CommentService;
import hello.studyWithGrade.service.StudyService;
import hello.studyWithGrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public String write(@Validated @ModelAttribute BoardForm boardForm, BindingResult result, @LoginUser SessionUser sessionUser, Model model) {

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

        User user = userService.findByEmail(sessionUser.getEmail());

        if (user.getRecentBoardWriteTime() != null && user.getRecentBoardWriteTime().getSecond() + 600 > LocalDateTime.now().getSecond()) {
            model.addAttribute("timeout", false);
            return "boards/boardForm";
        }

        model.addAttribute("timeout", true);

        if (result.hasErrors()) {
            return "boards/boardForm";
        }

        Board board = new Board();
        Study study = new Study();
        study.create(boardForm.getStudyName(), user);
        studyService.create(study);
        List<String> keywords = new ArrayList<>();
        for (String keyword : boardForm.getKeyword()) {
            keywords.add(keyword.split("\"")[3]);
        }
        board.create(boardForm.getTitle(), boardForm.getContent(), user, keywords, study);
        boardService.write(board);
        userService.updateWriteTime(user);

        return "redirect:/";
    }

    @GetMapping("/board/{boardId}")
    public String getBoard(@PathVariable("boardId") Long boardId, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                           Model model, @LoginUser SessionUser sessionUser, @RequestParam(value = "모집완료", required = false) String progress) {

        if (StringUtils.hasText(progress)) {
            boardService.finishRecruiting(boardService.findById(boardId));
            studyService.start(boardService.findById(boardId).getStudy());

            for (StudyMember studyMember : boardService.findById(boardId).getStudy().getStudyMembers()) {
                userService.start_study(studyMember.getUser());
            }

            return "redirect:/board/" + boardId;
        }

        Board board = boardService.findById(boardId);
        System.out.println(board.getKeyword().getClass().getSimpleName());
        Page<Comment> comments = commentService.findByBoard(board, pageable);
        model.addAttribute("boardDto", new BoardDto(board, comments, commentService.findByBoard(board).size()));
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("userDto", new UserDto(userService.findByEmail(sessionUser.getEmail())));

        return "boards/board";
    }

    @PostMapping("/board/{boardId}")
    public String recruiting(@Validated @ModelAttribute CommentForm commentForm, BindingResult result,
                             @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                             @PathVariable("boardId") Long boardId, Model model, @LoginUser SessionUser sessionUser) {

        if (!StringUtils.hasText(commentForm.getContent())) {
            result.rejectValue("content", "required", null, null);
        }

        if (result.hasErrors()) {
            model.addAttribute("boardDto", new BoardDto(boardService.findById(boardId), commentService.findByBoard(boardService.findById(boardId), pageable),
                    commentService.findByBoard(boardService.findById(boardId)).size()));
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
