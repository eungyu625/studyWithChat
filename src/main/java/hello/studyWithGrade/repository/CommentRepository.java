package hello.studyWithGrade.repository;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import hello.studyWithGrade.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUser(User user);
    List<Comment> findByBoard(Board board);

    Page<Comment> findByUser(User user, Pageable pageable);
    Page<Comment> findByBoard(Board board, Pageable pageable);
}
