package graduation.alcoholic.domain.repository;

import graduation.alcoholic.domain.entity.Image;
import graduation.alcoholic.domain.entity.Review;
import graduation.alcoholic.domain.entity.ReviewImage;
import graduation.alcoholic.domain.entity.ReviewImageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, ReviewImageId> {

    public List<Image> findByReview(Review review);
}
