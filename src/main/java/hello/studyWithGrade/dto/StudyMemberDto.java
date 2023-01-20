package hello.studyWithGrade.dto;

import lombok.Getter;

@Getter
public class StudyMemberDto {

    private Long id;
    private String email;

    protected StudyMemberDto() {

    }

    public StudyMemberDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
