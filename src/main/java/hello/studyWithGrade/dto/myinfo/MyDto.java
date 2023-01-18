package hello.studyWithGrade.dto.myinfo;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MyDto {

    private String email;

    protected MyDto() {

    }

    public MyDto(String email) {
        this.email = email;
    }
}
