package graduation.alcoholic.domain.repository;

import graduation.alcoholic.domain.entity.BarImage;
import graduation.alcoholic.domain.entity.BarImageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarImageRepository extends JpaRepository<BarImage, BarImageId> {
}
