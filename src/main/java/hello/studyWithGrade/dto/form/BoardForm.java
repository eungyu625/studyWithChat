package hello.studyWithGrade.dto.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BoardForm {

    private String title;
    private String content;
    private List<String> keyword;
}
