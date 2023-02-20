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
public class BarImage {

    @MapsId("bar_id")
    @ManyToOne
    private Bar bar;

    @MapsId("image_id")
    @ManyToOne
    private Image image;

    @EmbeddedId
    private BarImageId id;

    @Builder
    public BarImage(Bar bar, Image image) {
        this.bar = bar;
        this.image = image;
        this.id = new BarImageId();
    }
}
