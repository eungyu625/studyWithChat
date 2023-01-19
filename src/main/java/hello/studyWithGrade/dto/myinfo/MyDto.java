package hello.studyWithGrade.dto.myinfo;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MyDto {

    private Long id;
    private String email;

    protected MyDto() {

    }

    public MyDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
