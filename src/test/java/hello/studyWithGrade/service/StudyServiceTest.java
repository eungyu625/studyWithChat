package hello.studyWithGrade.service;

import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.user.Role;
import hello.studyWithGrade.entity.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class StudyServiceTest {

    @Autowired
    private StudyService studyService;

    @Autowired
    private UserService userService;

    @Test
    void create() {

        // given
        User user = new User();
        user.create("abc@naver.com", Role.USER);
        userService.join(user);
        Study study = new Study();
        study.create("study", user);
        studyService.create(study);

        // when
        Study findStudy = studyService.findById(study.getId());

        // then
        assertThat(findStudy).isEqualTo(study);
    }

    @Test
    void findStudyMember() {

        // given
        User user = new User();
        user.create("abc@naver.com", Role.USER);
        userService.join(user);
        Study study = new Study();
        study.create("study", user);
        studyService.create(study);

        // when
        Study findStudy = studyService.findById(study.getId());

        // then
        assertThat(List.of(user)).isEqualTo(userService.findStudyMembersByStudy(findStudy));
    }

    @Test
    void findByUser() {
    }

    @Test
    void addMember() {

        // given
        User user = new User();
        user.create("abc@naver.com", Role.USER);
        userService.join(user);
        Study study = new Study();
        study.create("study", user);
        studyService.create(study);

        // when
        User user2 = new User();
        user.create("qwer@gbbb.com", Role.USER);
        studyService.addMember(study, user2);

        Study findStudy = studyService.findById(study.getId());

        // then
        assertThat(List.of(user, user2)).isEqualTo(userService.findStudyMembersByStudy(findStudy));
    }
}
