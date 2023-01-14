package hello.studyWithGrade.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MainDto {

    private Long id;
    private String title;
    private String username;
    private String writeTime;
    private Integer commentNumber = 0;

    protected MainDto() {

    }

    public MainDto(Long id, String title, String username, LocalDateTime writeTime, Integer commentNumber) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.writeTime = DateTimeFormatter.ofPattern("MM-dd HH:mm").format(writeTime);
        if (commentNumber > 0) {
            this.commentNumber = commentNumber;
        }
    }
}
