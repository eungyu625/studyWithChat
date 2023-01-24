package hello.studyWithGrade.controller;

import hello.studyWithGrade.dto.MainDto;
import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.service.BoardService;
import hello.studyWithGrade.service.CommentService;
import hello.studyWithGrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/")
    public String home(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(defaultValue = "1") String page,
                       @RequestParam(value = "title", required = false) String title,
                       @RequestParam(value = "keyword", required = false) String keyword, Model model) {

        if (StringUtils.hasText(title)) {
            Page<Board> boardPage = boardService.findByTitleContaining(title, pageable);
            Page<MainDto> mainDtos = boardPage.map(board -> new MainDto(board.getId(), board.getTitle(), board.getUser(),
                    board.isProgress(), board.getWriteTime(), commentService.findByBoard(board).size()));
            model.addAttribute("mainDtos", mainDtos);
            return "home";
        }

        if (StringUtils.hasText(keyword)) {
            Page<Board> boardPage = boardService.findByKeyword(keyword, pageable);

            Page<MainDto> mainDtos = boardPage.map(board -> new MainDto(board.getId(), board.getTitle(), board.getUser(),
                    board.isProgress(), board.getWriteTime(), commentService.findByBoard(board).size()));
            model.addAttribute("mainDtos", mainDtos);
            return "home";
        }

        Page<Board> boardPage = boardService.findAll(pageable);

        Page<MainDto> mainDtos = boardPage.map(board -> new MainDto(board.getId(), board.getTitle(), board.getUser(),
                board.isProgress(), board.getWriteTime(), commentService.findByBoard(board).size()));

        model.addAttribute("mainDtos", mainDtos);

        return "home";
    }
}
