package hello.studyWithGrade.dto.page;

import hello.studyWithGrade.dto.MainDto;
import lombok.Getter;

import java.util.List;

@Getter
public class PageDto {

    private int total;
    private int currentPage;
    private int totalPages;
    private int startPage;
    private int endPage;
    private int pagingCount;
    private List<MainDto> mainDtos;

    public PageDto(int total, int currentPage, int size, int pagingCount, List<MainDto> mainDtos) {
        this.total = total;
        this.currentPage = currentPage;
        this.mainDtos.addAll(mainDtos);
        this.pagingCount = pagingCount;

        if (total == 0) {
            totalPages = 0;
            startPage = 0;
            endPage = 0;
        } else {
            totalPages = total / size;
            if (total % size > 0) {
                totalPages++;
            }
        }

        this.startPage = currentPage / pagingCount * pagingCount - 1;
        if (currentPage % pagingCount == 0) {
            startPage -= pagingCount;
        }
    }
}
