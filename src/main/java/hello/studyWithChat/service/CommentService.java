package hello.studyWithChat.service;

import hello.studyWithChat.entity.Board;
import hello.studyWithChat.entity.Comment;
import hello.studyWithChat.entity.user.User;
import hello.studyWithChat.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void write(Comment comment) {

        commentRepository.save(comment);
    }

    public List<Comment> findByUser(User user) {

        return commentRepository.findByUser(user);
    }

    public List<Comment> findByBoard(Board board) {

        return commentRepository.findByBoard(board);
    }
}
