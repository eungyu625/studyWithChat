package hello.studyWithGrade.service;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.manytomany.StudyMember;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StudyMemberService studyMemberService;

    public void join(User user) {
        userRepository.save(user);
    }

    public void withdrawal(User user) {
        userRepository.delete(user);
    }

    public User findById(Long id) {

        return userRepository.findById(id).orElse(null);
    }

    public void finish_study(User user) {
        user.end_study();
        userRepository.save(user);
    }

    public void start_study(User user) {
        user.start_study();
        userRepository.save(user);
    }

    public void estimate(User user, Double grade) {
        user.estimated(grade);
        userRepository.save(user);
    }

    public void updateWriteTime(User user) {
        user.updateRecentBoardWriteTime();
        userRepository.save(user);
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email).orElse(null);
    }

    // 해당 스터디의 스터디원 정보 반환
    public List<User> findStudyMembersByStudy(Study study) {

        return studyMemberService.findByStudy(study).stream()
                .map(StudyMember::getUser)
                .collect(Collectors.toList());
    }
}
