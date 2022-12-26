package hello.studyWithGrade.dto;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.user.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private User user;
    private boolean progress;
    private List<CommentDto> commentDtos;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.user = board.getUser();
        this.progress = board.isProgress();
        this.commentDtos = board.getComments().stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }
}
