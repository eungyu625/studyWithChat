package hello.studyWithGrade.repository;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitleLike(String title);
    List<Board> findByUser(User user);
    Page<Board> findAll(Pageable pageable);
}
