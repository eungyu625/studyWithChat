package hello.studyWithGrade.dto;

import hello.studyWithGrade.entity.Comment;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class CommentDto {

    private Long id;
    private Long boardId;
    private String content;
    private String writeTime;
    private UserDto userDto;

    protected CommentDto() {

    }

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.boardId = comment.getBoard().getId();
        this.content = comment.getContent();
        this.writeTime = DateTimeFormatter.ofPattern("HH:mm").format(comment.getWriteTime());
        this.userDto = new UserDto(comment.getUser());
    }
}
