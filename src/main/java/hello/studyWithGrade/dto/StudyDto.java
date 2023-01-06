package hello.studyWithGrade.dto;

import hello.studyWithGrade.entity.Study;
import hello.studyWithGrade.entity.user.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StudyDto {

    private Long id;
    private String name;
    private boolean progress;
    private List<UserDto> userDtos;

    protected StudyDto() {

    }

    public StudyDto(Study study, List<User> users) {
        this.id = study.getId();
        this.name = study.getName();
        this.progress = study.isProgress();
        this.userDtos = users.stream().map(UserDto::new).collect(Collectors.toList());
    }
}
