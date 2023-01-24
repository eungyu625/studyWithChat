package hello.studyWithGrade.repository;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByTitleContaining(String title, Pageable pageable);
    Page<Board> findByUser(User user, Pageable pageable);
    Page<Board> findAll(Pageable pageable);

    List<Board> findAll();
}
