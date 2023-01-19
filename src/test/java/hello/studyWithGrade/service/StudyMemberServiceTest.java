package hello.studyWithGrade.service;

import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.manytomany.StudyMember;
import hello.studyWithGrade.entity.user.Role;
import hello.studyWithGrade.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StudyMemberServiceTest {

    @Autowired
    StudyMemberService studyMemberService;

    @Autowired
    StudyService studyService;

    @Autowired
    UserService userService;

    @Test
    void findByUser() {
        User user = new User();
        user.create("agyu625", Role.USER);
        userService.join(user);

        Study study = new Study();
        study.create("spring", user);

        studyService.create(study);

        Study study2 = new Study();
        study2.create("java", user);

        studyService.create(study2);

        for (StudyMember studyMember : studyMemberService.findByUser(user)) {
            System.out.println(studyMember.getStudy().getName());
        }

    }
}
