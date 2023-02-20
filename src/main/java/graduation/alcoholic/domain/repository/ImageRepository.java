package graduation.alcoholic.domain.repository;

import graduation.alcoholic.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    public Optional<Image> findByImage(String image);
}
