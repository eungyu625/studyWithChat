package hello.studyWithGrade.entity;

import hello.studyWithGrade.entity.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    /*
     * 생성 메소드
     */
    public void create(String content, Board board, User user) {
        this.content = content;
        this.board = board;
        this.user = user;
    }
}
