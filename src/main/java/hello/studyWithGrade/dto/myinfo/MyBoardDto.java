package hello.studyWithGrade.dto.myinfo;

import hello.studyWithGrade.entity.Board;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

/*
 * 내 게시글을 조회할 때 사용하는 DTO
 */
@Getter
public class MyBoardDto {

    private Long id;
    private String title;
    private String writeTime;
    private boolean progress;
    private Integer commentNumber = 0;

    protected MyBoardDto() {

    }

    public MyBoardDto(Board board, Integer commentNumber) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.progress = board.isProgress();
        this.writeTime = DateTimeFormatter.ofPattern("MM-dd HH:mm").format(board.getWriteTime());
        if (commentNumber > 0) {
            this.commentNumber = commentNumber;
        }
    }
}
