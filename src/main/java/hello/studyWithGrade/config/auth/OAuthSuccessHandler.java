package hello.studyWithGrade.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.studyWithGrade.config.auth.dto.SessionUser;
import hello.studyWithGrade.config.auth.dto.UserRequestMapper;
import hello.studyWithGrade.config.jwt.Token;
import hello.studyWithGrade.config.jwt.TokenService;
import hello.studyWithGrade.entity.user.Role;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        System.out.println(oAuth2User);
        System.out.println(oAuth2User.getAttributes());

        // 최초 로그인이라면 회원가입 처리를 한다.
        User user = new User();
        user.create((String)oAuth2User.getAttributes().get("email"), Role.USER);
        userService.join(user);

        SessionUser userDto = userRequestMapper.toDto(oAuth2User);

        Token token = tokenService.generateToken(userDto.getEmail(), "ROLE_USER");

        writeTokenResponse(response, token);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}