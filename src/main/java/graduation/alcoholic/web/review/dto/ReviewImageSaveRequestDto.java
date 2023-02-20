package graduation.alcoholic.web.review.dto;

import graduation.alcoholic.domain.entity.CollectionContent;
import graduation.alcoholic.domain.entity.Image;
import graduation.alcoholic.domain.entity.Review;
import graduation.alcoholic.domain.entity.ReviewImage;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewImageSaveRequestDto {

    private Review review;
    private List<Image> imageList;

    @Builder
    public ReviewImageSaveRequestDto(Review review, List<Image> imageList) {
        this.review = review;
        this.imageList = imageList;
    }

    public List<ReviewImage> toEntity() {

        List<ReviewImage> reviewImageList = new ArrayList<>();
        imageList.forEach(image ->
                reviewImageList.add(ReviewImage.builder()
                        .review(review)
                        .image(image)
                        .build()));

        return reviewImageList;
    }

}
