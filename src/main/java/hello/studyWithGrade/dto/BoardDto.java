package hello.studyWithGrade.dto;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private UserDto userDto;
    private List<CommentDto> commentDtos;

    protected BoardDto() {

    }

    public BoardDto(Board board, List<Comment> comments) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userDto = new UserDto(board.getUser());
        this.commentDtos = comments.stream().map(CommentDto::new).collect(Collectors.toList());
    }
}