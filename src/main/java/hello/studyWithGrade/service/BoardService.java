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

        User user = board.getUser();
        user.updateRecentBoardWriteTime(); // 사용자가 가장 최근에 글을 작성한 시간 갱신 -> 10분 시간 제한을 두기 위해 필요
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
