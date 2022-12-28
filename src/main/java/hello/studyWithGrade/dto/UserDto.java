package hello.studyWithGrade.dto;

import hello.studyWithGrade.entity.user.User;
import lombok.Getter;

/*
 * 타인의 정보를 조회할 때 사용하는 DTO
 */
@Getter
public class UserDto {

    private Long id;
    private String email;
    private Double grade;
    private Integer studies_ing;
    private Integer studies_ended;

    protected UserDto() {

    }

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.grade = user.getGrade();
        this.studies_ing = user.getStudies_ing();
        this.studies_ended = user.getStudies_ended();
    }
}
