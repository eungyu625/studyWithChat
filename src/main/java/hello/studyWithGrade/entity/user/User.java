package hello.studyWithGrade.entity.user;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.manytomany.StudyMember;
import lombok.Getter;

import javax.persistence.*;
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

    /*
     * 생성 메소드
     */
    public void create(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    public String getRoleKey() {

        return role.getKey();
    }
}
