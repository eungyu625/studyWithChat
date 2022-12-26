package hello.studyWithGrade.repository;

import hello.studyWithGrade.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
