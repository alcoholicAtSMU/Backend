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
public class BarImageId implements Serializable {

    public Long bar_id;

    public Long image_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarImageId that = (BarImageId) o;
        return bar_id.equals(that.bar_id) && image_id.equals(that.image_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bar_id, image_id);
    }

    @Builder
    public BarImageId(Long bar_id, Long image_id) {
        this.bar_id = bar_id;
        this.image_id = image_id;
    }
}
