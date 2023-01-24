package hello.studyWithGrade.entity.user;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.manytomany.StudyMember;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    private String email;

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<StudyMember> studyMembers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    private Double grade = 0.;

    private Integer estimatedNUmber = 0;

    private Integer studies_ended = 0;

    private Integer studies_ing = 0;

    private LocalDateTime recentBoardWriteTime;

    /*
     * 생성 메소드
     */
    public void create(String email, Role role) {
        this.email = email.split("@")[0];
        this.role = role;
    }

    public void updateRecentBoardWriteTime() {
        this.recentBoardWriteTime = LocalDateTime.now();
    }

    public void start_study() {
        studies_ing += 1;
    }

    public void end_study() {
        studies_ing -= 1;
        studies_ended += 1;

    }

    public void estimated(Double new_grade) {
        double sum = grade * estimatedNUmber + new_grade;
        estimatedNUmber += 1;
        grade = sum / estimatedNUmber;
    }

    public String getRoleKey() {

        return role.getKey();
    }
}
