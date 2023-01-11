package hello.studyWithGrade.config.auth.dto;

import hello.studyWithGrade.entity.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String email;

    public SessionUser(String email) {
        this.email = email;
    }
}
