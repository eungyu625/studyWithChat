package hello.studyWithGrade.service;

import hello.studyWithGrade.entity.user.Role;
import hello.studyWithGrade.entity.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void findById() {
        User user = new User("abc@naver.com", Role.USER);
        userService.join(user);
        User findUser = userService.findById(user.getId());

        assertThat(findUser).isEqualTo(user);
    }
}
