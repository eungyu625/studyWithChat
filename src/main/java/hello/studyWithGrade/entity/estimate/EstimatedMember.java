package hello.studyWithGrade.entity.estimate;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class EstimatedMember {

    private List<Long> estimatedMemberIdList = new ArrayList<>();

    public void estimated(Long id) {
        estimatedMemberIdList.add(id);
    }
}
