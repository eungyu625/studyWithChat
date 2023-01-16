package hello.studyWithGrade.dto.myinfo;

import hello.studyWithGrade.entity.Comment;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

/*
 * 내 댓글을 조회할 때 사용하는 DTO
 */
@Getter
public class MyCommentDto {

    private Long id;
    private String content;
    private Long boardId;
    private String boardTitle;
    private String writeTime;

    protected MyCommentDto() {

    }

    public MyCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.boardId = comment.getBoard().getId();
        this.boardTitle = comment.getBoard().getTitle();
        this.writeTime = DateTimeFormatter.ofPattern("MM-dd HH:mm").format(comment.getWriteTime());
    }
}
