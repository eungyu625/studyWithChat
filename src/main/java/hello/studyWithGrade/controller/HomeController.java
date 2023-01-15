package hello.studyWithGrade.controller;

import hello.studyWithGrade.dto.BoardDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/")
    public String home(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        for (Board board : boardService.findAll(pageable)) {
            System.out.println(board.getTitle());
        }

        model.addAttribute("mainDtos", boardService.findAll(pageable));

        return "home";
    }

    @PostMapping("/")
    public String searchByTitle(@RequestParam String title, Model model) {

        List<MainDto> mainDtos = new ArrayList<>();

        for (Board board : boardService.findByTitleLike(title)) {
            mainDtos.add(new MainDto(board.getId(), board.getTitle(), board.getUser(), board.getWriteTime(),
                    commentService.findByBoard(board).size()));
        }

        model.addAttribute("mainDtoList", mainDtos);

        return "redirect:/";
    }
}
