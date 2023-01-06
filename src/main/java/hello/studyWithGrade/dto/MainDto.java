package hello.studyWithGrade.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MainDto {

    private Long id;
    private String title;
    private String username;
    private LocalDateTime writeTime;
    private Integer commentNumber;

    protected MainDto() {

    }

    public MainDto(Long id, String title, String username, LocalDateTime writeTime, Integer commentNumber) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.writeTime = writeTime;
        this.commentNumber = commentNumber;
    }
}
