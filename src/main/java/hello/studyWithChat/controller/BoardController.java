package hello.studyWithChat.controller;

import hello.studyWithChat.dto.BoardDto;
import hello.studyWithChat.entity.Board;
import hello.studyWithChat.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public List<BoardDto> findBoardsAll() {
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardService.findAll()) {
            boardDtos.add(new BoardDto(board));
        }

        return boardDtos;
    }
}
