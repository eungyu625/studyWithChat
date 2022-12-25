package hello.studyWithChat.repository;

import hello.studyWithChat.entity.Board;
import hello.studyWithChat.entity.Comment;
import hello.studyWithChat.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUser(User user);
    List<Comment> findByBoard(Board board);
}
