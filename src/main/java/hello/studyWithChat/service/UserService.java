package hello.studyWithChat.service;

import hello.studyWithChat.entity.Board;
import hello.studyWithChat.entity.Comment;
import hello.studyWithChat.entity.user.User;
import hello.studyWithChat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void join(User user) {
        userRepository.save(user);
    }

    public void writeBoard(User user, Board board) {
        user.writeBoard(board);
        userRepository.save(user);
    }

    public void writeComment(User user, Comment comment) {
        user.writeComment(comment);
        userRepository.save(user);
    }

    public User findUserById(Long id) {

        return userRepository.findById(id).orElse(null);
    }

    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email).orElse(null);
    }
}
