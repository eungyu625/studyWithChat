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
    private StudyMemberDto owner;
    private boolean progress;
    private List<StudyMemberDto> notEstimatedMembers;
    private List<StudyMemberDto> estimatedMembers;

    protected StudyDto() {

    }

    public StudyDto(Study study, List<User> notEstimatedMembers, List<User> estimatedMembers) {
        this.id = study.getId();
        this.name = study.getName();
        this.owner = new StudyMemberDto(study.getUser().getId(), study.getUser().getEmail());
        this.progress = study.isProgress();
        this.notEstimatedMembers = notEstimatedMembers.stream().map(studyMember -> new StudyMemberDto(studyMember.getId(), studyMember.getEmail())).collect(Collectors.toList());
        this.estimatedMembers = estimatedMembers.stream().map(studyMember -> new StudyMemberDto(studyMember.getId(), studyMember.getEmail())).collect(Collectors.toList());
    }
}
