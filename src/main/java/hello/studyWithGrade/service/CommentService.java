package hello.studyWithGrade.service;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final BoardService boardService;

    public void write(Comment comment) {
        commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public Comment findById(Long id) {

        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> findByUser(User user) {

        return commentRepository.findByUser(user);
    }

    public List<Comment> findByBoard(Board board) {

        return commentRepository.findByBoard(board);
    }
}
