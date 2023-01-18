package hello.studyWithGrade.repository;

import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.manytomany.StudyMember;
import hello.studyWithGrade.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    List<StudyMember> findByUser(User user);
    List<StudyMember> findByStudy(Study study);

    Page<StudyMember> findByUser(User user, Pageable pageable);
    Page<StudyMember> findByStudy(Study study, Pageable pageable);
}
