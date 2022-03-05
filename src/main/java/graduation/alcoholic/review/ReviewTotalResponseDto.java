package graduation.alcoholic.review;

import graduation.alcoholic.domain.enums.Taste;
import lombok.Builder;

public class ReviewTotalResponseDto {

    private Double total_star;

    private Taste top_taste_1;
    private Taste top_taste_2;
    private Taste top_taste_3;
    private Taste top_taste_4;
    private Taste top_taste_5;

    @Builder
    public ReviewTotalResponseDto(Double total_star, Taste top_taste_1, Taste top_taste_2, Taste top_taste_3, Taste top_taste_4, Taste top_taste_5) {
        this.total_star = total_star;
        this.top_taste_1 = top_taste_1;
        this.top_taste_2 = top_taste_2;
        this.top_taste_3 = top_taste_3;
        this.top_taste_4 = top_taste_4;
        this.top_taste_5 = top_taste_5;
    }
}
