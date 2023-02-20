package graduation.alcoholic.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImageId implements Serializable {

    public Long review_id;

    public Long image_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarImageId that = (BarImageId) o;
        return review_id.equals(that.bar_id) && image_id.equals(that.image_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(review_id, image_id);
    }

    @Builder
    public ReviewImageId(Long bar_id, Long image_id) {
        this.review_id = review_id;
        this.image_id = image_id;
    }
}
