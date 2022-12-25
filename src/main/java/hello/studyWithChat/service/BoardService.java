package hello.studyWithChat.service;

import hello.studyWithChat.entity.Board;
import hello.studyWithChat.entity.user.User;
import hello.studyWithChat.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void write(Board board) {
        boardRepository.save(board);
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
