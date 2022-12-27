package hello.studyWithGrade.service;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.user.Role;
import hello.studyWithGrade.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Test
    void findById(){

        // given
        User user = new User();
        user.create("123", Role.USER);
        userService.join(user);
        Board board = new Board();
        board.create("title", "content", user);
        boardService.write(board);

        // when
        Board findBoard = boardService.findById(board.getId());

        // then
        assertThat(findBoard).isEqualTo(board);
    }

    @Test
    void delete() {

        // given
        User user = new User();
        user.create("123", Role.USER);
        userService.join(user);
        Board board = new Board();
        board.create("title", "content", user);
        Board board2 = new Board();
        board2.create("title", "content", user);
        boardService.write(board);
        boardService.write(board2);

        List<Board> boards = new ArrayList<>();
        boards.addAll(boardService.findAll());

        assertThat(2).isEqualTo(boards.size());

        // when
        boardService.delete(board);

        // then
        List<Board> findBoards = new ArrayList<>();
        findBoards.addAll(boardService.findAll());

        assertThat(1).isEqualTo(findBoards.size());
    }
}