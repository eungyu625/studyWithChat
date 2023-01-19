package hello.studyWithGrade.dto.myinfo;

import hello.studyWithGrade.entity.Study;
import lombok.Getter;

/*
 * 내 스터디를 조회할 때 사용하는 DTO
 */
@Getter
public class MyStudyDto {

    private Long id;
    private String name;
    private Long ownerId;
    private String ownerName;
    private Integer memberNumber;
    private boolean progress;
    private boolean start;

    protected MyStudyDto() {

    }

    public MyStudyDto(Study study) {
        this.id = study.getId();
        this.name = study.getName();
        this.ownerId = study.getUser().getId();
        this.ownerName = study.getUser().getEmail();
        this.memberNumber = study.getStudyMembers().size();
        this.progress = study.isProgress();
        this.start = study.isStart();
    }
}
