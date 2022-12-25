package hello.studyWithChat.controller;

import hello.studyWithChat.config.auth.LoginUser;
import hello.studyWithChat.config.auth.dto.SessionUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/test")
    public SessionUser demo(@LoginUser SessionUser user) {

        return user;
    }
}
