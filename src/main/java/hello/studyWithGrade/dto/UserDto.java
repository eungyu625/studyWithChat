package hello.studyWithGrade.dto;

import hello.studyWithGrade.entity.user.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserDto {

    private String email;
    private List<BoardDto> boardDtos;
    private List<CommentDto> commentDtos;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.boardDtos = user.getBoards().stream()
                .map(board -> new BoardDto(board))
                .collect(Collectors.toList());
        this.commentDtos = user.getComments().stream()
                .map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());
    }
}
