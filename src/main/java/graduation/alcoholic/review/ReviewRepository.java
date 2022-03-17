package graduation.alcoholic.review;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.Review;
import graduation.alcoholic.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByAlcoholOrderByModifiedDateDesc(Alcohol alcohol);

    List<Review> findByUserOrderByModifiedDateDesc(User user);

    Optional<Review> findByUserAndAlcohol(User user,Alcohol alcohol);
}
