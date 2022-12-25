package hello.studyWithChat.dto;

import hello.studyWithChat.entity.Board;
import hello.studyWithChat.entity.Comment;
import hello.studyWithChat.entity.user.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardDto {

    private String title;
    private String contents;
    private User user;
    private List<CommentDto> commentDtos;

    public BoardDto(Board board) {
        this.title = board.getTitle();
        this.contents = board.getContent();
        this.user = board.getUser();
        this.commentDtos = board.getComments().stream()
                .map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());
    }
}
