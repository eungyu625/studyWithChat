package hello.studyWithGrade.dto.myinfo;

import hello.studyWithGrade.entity.Board;
import hello.studyWithGrade.entity.Comment;
import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.user.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/*
 * 내 정보를 조회할 때 사용하는 DTO
 */

@Getter
public class MyInfoDto {

    private Long id;
    private String email;
    private Double grade;
    private Integer studies_ing;
    private Integer studies_ended;
    private List<MyBoardDto> myBoardDtos; // 내가 쓴 게시글에 대한 dto 리스트
    private List<MyCommentDto> myCommentDtos; // 내가 쓴 댓글에 대한 dto 리스트
    private List<MyStudyDto> myStudyDtos; // 내가 가입한 스터디에 대한 dto 리스트

    protected MyInfoDto() {

    }

    public MyInfoDto(User user, List<Board> boards, List<Comment> comments, List<Study> studies) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.grade = user.getGrade();
        this.studies_ing = user.getStudies_ing();
        this.studies_ended = user.getStudies_ended();
        this.myBoardDtos = boards.stream().map(MyBoardDto::new).collect(Collectors.toList());
        this.myCommentDtos = comments.stream().map(MyCommentDto::new).collect(Collectors.toList());
        this.myStudyDtos = studies.stream().map(MyStudyDto::new).collect(Collectors.toList());
    }

}
