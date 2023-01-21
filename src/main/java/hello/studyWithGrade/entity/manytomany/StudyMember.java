package hello.studyWithGrade.entity.manytomany;

import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
 * User와 Study Entity 간의 ManyToMany 관계를 해결하기 위한 클래스
 */
@Entity
@Getter
public class StudyMember {

    @Id
    @GeneratedValue
    @Column(name = "studyMember_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @ElementCollection
    private List<Long> estimatedMembers;

    protected StudyMember() {

    }

    public StudyMember(Study study, User user) {
        this.study = study;
        this.user = user;
    }

    public void estimated(User user) {
        estimatedMembers.add(user.getId());
    }
}
