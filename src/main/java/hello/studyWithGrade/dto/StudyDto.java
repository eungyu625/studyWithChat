package hello.studyWithGrade.dto;

import hello.studyWithGrade.entity.Study;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StudyDto {

    private String name;
    private boolean progress;
    private List<UserDto> members;

    public StudyDto(Study study) {
        this.name = study.getName();
        this.progress = study.isProgress();
        members = study.getStudyMembers().stream()
                .map(studyMember -> new UserDto(studyMember.getUser()))
                .collect(Collectors.toList());
    }
}
