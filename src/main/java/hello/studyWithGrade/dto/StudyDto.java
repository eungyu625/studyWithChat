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
    private UserDto owner;
    private boolean progress;
    private List<StudyMemberDto> studyMemberDtos;

    protected StudyDto() {

    }

    public StudyDto(Study study, List<User> users) {
        this.id = study.getId();
        this.name = study.getName();
        this.owner = new UserDto(study.getUser());
        this.progress = study.isProgress();
        this.studyMemberDtos = users.stream().map(user -> new StudyMemberDto(user.getId(), user.getEmail())).collect(Collectors.toList());
    }
}
