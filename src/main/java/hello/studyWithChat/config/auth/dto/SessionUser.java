package hello.studyWithChat.config.auth.dto;

import hello.studyWithChat.entity.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String email;

    public SessionUser(User user) {
        this.email = user.getEmail();
    }
}
