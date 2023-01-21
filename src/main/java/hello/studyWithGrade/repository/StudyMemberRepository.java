package hello.studyWithGrade.repository;

import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.manytomany.StudyMember;
import hello.studyWithGrade.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    Optional<StudyMember> findByUserAndStudy(User user, Study study);

    List<StudyMember> findByUser(User user);
    List<StudyMember> findByStudy(Study study);

    Page<StudyMember> findByUser(User user, Pageable pageable);
    Page<StudyMember> findByStudy(Study study, Pageable pageable);

    @Query(value = "select studyMember " +
            "from StudyMember studyMember " +
            "where studyMember.user = :user and studyMember.study.start = false")
    Page<StudyMember> findByUserWhichIsStarted(@Param("user") User user, Pageable pageable);
}
