package hello.studyWithGrade.repository;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByTitleLike(String title, Pageable pageable);
    Page<Board> findByUser(User user, Pageable pageable);
    Page<Board> findAll(Pageable pageable);
}
