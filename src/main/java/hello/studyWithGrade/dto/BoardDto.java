package hello.studyWithGrade.dto;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private boolean progress;
    private String writeTime;
    private UserDto userDto;
    private List<CommentDto> commentDtos;
    private Integer commentNumbers;

    protected BoardDto() {

    }

    public BoardDto(Board board, List<Comment> comments) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.progress = board.isProgress();
        this.writeTime = DateTimeFormatter.ofPattern("MM-dd HH:mm").format(board.getWriteTime());
        this.userDto = new UserDto(board.getUser());
        this.commentDtos = comments.stream().map(CommentDto::new).collect(Collectors.toList());
        this.commentNumbers = commentDtos.size();
    }
}
