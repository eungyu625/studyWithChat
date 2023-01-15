package hello.studyWithGrade.dto;

import hello.studyWithGrade.entity.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MainDto {

    private Long id;
    private String title;
    private UserDto userDto;
    private String writeTime;
    private Integer commentNumber = 0;

    protected MainDto() {

    }

    public MainDto(Long id, String title, User user, LocalDateTime writeTime, Integer commentNumber) {
        this.id = id;
        this.title = title;
        this.userDto = new UserDto(user);
        this.writeTime = DateTimeFormatter.ofPattern("MM-dd HH:mm").format(writeTime);
        if (commentNumber > 0) {
            this.commentNumber = commentNumber;
        }
    }
}
