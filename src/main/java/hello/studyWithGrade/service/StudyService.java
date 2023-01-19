package hello.studyWithGrade.service;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.manytomany.StudyMember;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyMemberService studyMemberService;

    public void create(Study study) {
        studyMemberService.save(new StudyMember(study, study.getUser()));
        studyRepository.save(study);
    }

    // 스터디장이 탈퇴할 경우에만 스터디 삭제
    public void delete(Study study) {
        studyMemberService.deleteStudy(studyMemberService.findByStudy(study));
        studyRepository.delete(study);
    }

    public Study findById(Long id) {

        return studyRepository.findById(id).orElse(null);
    }

    // 해당 유저의 스터디 정보 반환
    public List<Study> findByUser(User user) {

        return studyMemberService.findByUser(user).stream()
                .map(StudyMember::getStudy)
                .collect(Collectors.toList());
    }

    // 스터디원 추가
    public void addMember(Study study, User user) {
        studyMemberService.save(new StudyMember(study, user));
    }

    // 탈퇴한 스터디원 정보 삭제
    public void removeMember(User user) {
        studyMemberService.deleteMember(studyMemberService.findByUser(user));
    }

    public void finish(Study study) {
        study.finish();
        studyRepository.save(study);
    }

    public void start(Study study) {
        study.startStudy();
        studyRepository.save(study);
    }
}
