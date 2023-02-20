package graduation.alcoholic.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ReviewImage {

    @MapsId("review_id")
    @ManyToOne
    private Review review;

    @MapsId("image_id")
    @ManyToOne
    private Image image;

    @EmbeddedId
    private ReviewImageId id;

    @Builder
    public ReviewImage(Review review, Image image) {
        this.review = review;
        this.image = image;
        this.id = new ReviewImageId();
    }
}
