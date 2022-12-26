package hello.studyWithGrade.dto;

import hello.studyWithGrade.entity.Comment;
import lombok.Getter;

@Getter
public class CommentDto {

    private String contents;
    private BoardDto boardDto;
    private UserDto userDto;

    public CommentDto(Comment comment) {
        this.contents = comment.getContent();
        this.boardDto = new BoardDto(comment.getBoard());
        this.userDto = new UserDto(comment.getUser());
    }
}
