package graduation.alcoholic.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Getter
@NoArgsConstructor
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

}
