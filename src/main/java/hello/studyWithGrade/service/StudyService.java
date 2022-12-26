package hello.studyWithGrade.service;

import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.manytomany.StudyMember;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {

    private final StudyRepository studyRepository;

    public void create(Study study) {
        studyRepository.save(study);
    }

    public Study findById(Long id) {

        return studyRepository.findById(id).orElse(null);
    }

    // 해당 유저의 스터디 정보 반환
    public List<Study> findByUser(User user) {

        return user.getStudyMembers().stream()
                .map(StudyMember::getStudy)
                .collect(Collectors.toList());
    }

    // 탈퇴한 스터디원 정보 삭제
    public void removeMember(User user) {

    }
}
