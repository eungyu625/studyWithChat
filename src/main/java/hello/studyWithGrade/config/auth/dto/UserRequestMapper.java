package hello.studyWithGrade.config.auth.dto;

import hello.studyWithGrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRequestMapper {

    private final UserService userService;

    public SessionUser toDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return new SessionUser(userService.findByEmail((String)attributes.get("email")));
    }
}