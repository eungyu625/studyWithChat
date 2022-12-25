package hello.studyWithChat.controller;

import hello.studyWithChat.dto.CommentDto;
import hello.studyWithChat.entity.Comment;
import hello.studyWithChat.service.CommentService;
import hello.studyWithChat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/{userId}/comments")
    public List<CommentDto> findByUserId(@PathVariable Long userId) {
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : commentService.findByUser(userService.findUserById(userId))) {
            commentDtos.add(new CommentDto(comment));
        }

        return commentDtos;
    }
}
