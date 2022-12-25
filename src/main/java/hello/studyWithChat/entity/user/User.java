package hello.studyWithChat.entity.user;

import hello.studyWithChat.entity.Board;
import hello.studyWithChat.entity.Comment;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {

    }

    public User(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    public void writeBoard(Board board) {
        boards.add(board);
    }

    public void writeComment(Comment comment) {
        comments.add(comment);
    }

    public String getRoleKey() {

        return role.getKey();
    }
}
