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
    private boolean progress;

    protected MyStudyDto() {

    }

    public MyStudyDto(Study study) {
        this.id = study.getId();
        this.name = study.getName();
        this.progress = study.isProgress();
    }
}
