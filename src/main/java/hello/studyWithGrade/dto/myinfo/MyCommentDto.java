package hello.studyWithGrade.dto.myinfo;

import hello.studyWithGrade.entity.Comment;
import lombok.Getter;

/*
 * 내 댓글을 조회할 때 사용하는 DTO
 */
@Getter
public class MyCommentDto {

    private Long id;
    private String content;

    protected MyCommentDto() {

    }

    public MyCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }
}
