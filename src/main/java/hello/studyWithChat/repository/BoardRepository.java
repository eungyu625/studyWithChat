package hello.studyWithChat.repository;

import hello.studyWithChat.entity.Board;
import hello.studyWithChat.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitleLike(String title);
    List<Board> findByUser(User user);
}
