package hello.studyWithGrade.service;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    public void write(Board board) {
        boardRepository.save(board);
    }

    public void delete(Board board) {
        boardRepository.delete(board);
    }

    public Board findById(Long id) {

        return boardRepository.findById(id).orElse(null);
    }

    public List<Board> findAll() {

        return boardRepository.findAll();
    }

    public List<Board> findByTitleLike(String title) {

        return boardRepository.findByTitleLike(title);
    }

    public List<Board> findByUser(User user) {

        return  boardRepository.findByUser(user);
    }

}
