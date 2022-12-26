package hello.studyWithGrade.service;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
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
public class CommentServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;

    @Test
    void findById() {

        // given
        User user = new User("123", Role.USER);
        userService.join(user);

        Board board = new Board("abc", "abc", user);
        boardService.write(board);

        Comment comment = new Comment("aaaa", board, user);
        commentService.write(comment);

        // when
        Comment findComment = commentService.findById(comment.getId());

        // then
        assertThat(comment).isEqualTo(findComment);
    }

    @Test
    void findByBoard() {

        // given
        User user = new User("123", Role.USER);
        userService.join(user);

        Board board = new Board("abc", "abc", user);
        boardService.write(board);

        Comment comment = new Comment("aaaa", board, user);
        commentService.write(comment);

        // when
        List<Comment> findComments = new ArrayList<>();
        findComments.addAll(commentService.findByBoard(board));
        Comment findComment = findComments.get(0);

        // then
        assertThat("aaaa").isEqualTo(findComment.getContent());
    }

    @Test
    void delete() {

        // given
        User user = new User("123", Role.USER);
        userService.join(user);

        Board board = new Board("abc", "abc", user);
        boardService.write(board);

        Comment comment = new Comment("aaaa", board, user);
        commentService.write(comment);

        assertThat(1).isEqualTo(commentService.findByBoard(board).size());

        // when
        commentService.delete(comment);

        // then
        assertThat(0).isEqualTo(commentService.findByBoard(board).size());
    }
}
