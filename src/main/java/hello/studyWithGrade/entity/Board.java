package hello.studyWithGrade.entity;

import hello.studyWithGrade.entity.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    private boolean progress = false;

    private LocalDateTime writeTime;

    @ElementCollection
    private List<String> keyword;

    /*
     * 생성 메소드
     */
    public void create(String title, String content, User user, List<String> keyword, Study study) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.study = study;
        this.writeTime = LocalDateTime.now();
        this.keyword = keyword.stream().toList();
    }

    public void finish() {
        progress = true;
    }
}
