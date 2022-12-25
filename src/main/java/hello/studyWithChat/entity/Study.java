package hello.studyWithChat.entity;

import hello.studyWithChat.entity.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Study {

    @Id
    @GeneratedValue
    @Column(name = "study_id")
    private Long id;

    private String name;

    private boolean progress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
