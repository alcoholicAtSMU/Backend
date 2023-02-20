package graduation.alcoholic.web.image;

import graduation.alcoholic.domain.entity.Image;
import graduation.alcoholic.domain.entity.Review;
import graduation.alcoholic.domain.entity.ReviewImage;
import graduation.alcoholic.domain.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewImageService {

    private final ReviewImageRepository reviewImageRepository;

    @Transactional
    public void save(Review review, List<Image> imageList) {

        List<ReviewImage> reviewImageList = new ArrayList<>();
        for (Image image : imageList) {
            ReviewImage reviewImage = new ReviewImage(review, image);
            reviewImageList.add(reviewImage);
        }
        reviewImageRepository.saveAll(reviewImageList);
    }

    @Transactional(readOnly = true)
    public List<Image> findImageByReview(Review review) {

        return reviewImageRepository.findByReview(review);
    }


}

