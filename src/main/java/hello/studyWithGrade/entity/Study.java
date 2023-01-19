package hello.studyWithGrade.entity;

import hello.studyWithGrade.entity.manytomany.StudyMember;
import hello.studyWithGrade.entity.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Study {

    @Id
    @GeneratedValue
    @Column(name = "study_id")
    private Long id;

    private String name;

    private boolean progress = false;

    private boolean start = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "study")
    private List<StudyMember> studyMembers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Board board;

    /*
     * 생성 메소드
     */
    public void create(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void startStudy() {
        start = true;
    }

    public void finish() {
        progress = true;
    }
}
