package hello.studyWithGrade.service;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import hello.studyWithGrade.entity.user.User;
import hello.studyWithGrade.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    public Page<Board> findAll(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    public Page<Board> findByTitleLike(String title, Pageable pageable) {

        return boardRepository.findByTitleLike(title, pageable);
    }

    public Page<Board> findByUser(User user, Pageable pageable) {

        return  boardRepository.findByUser(user, pageable);
    }

    public void finishRecruiting(Board board) {
        board.finish();
        boardRepository.save(board);
    }

}
