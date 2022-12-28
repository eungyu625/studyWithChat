package hello.studyWithGrade.dto;

import hello.studyWithGrade.entity.Comment;
import lombok.Getter;

@Getter
public class CommentDto {

    private Long id;
    private String content;
    private UserDto userDto;

    protected CommentDto() {

    }

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userDto = new UserDto(comment.getUser());
    }
}
