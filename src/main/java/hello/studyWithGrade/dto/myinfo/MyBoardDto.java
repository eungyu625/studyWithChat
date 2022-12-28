package hello.studyWithGrade.dto.myinfo;

import hello.studyWithGrade.entity.Board;
import lombok.Getter;

/*
 * 내 게시글을 조회할 때 사용하는 DTO
 */
@Getter
public class MyBoardDto {

    private Long id;
    private String title;
    private String content;

    protected MyBoardDto() {

    }

    public MyBoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
