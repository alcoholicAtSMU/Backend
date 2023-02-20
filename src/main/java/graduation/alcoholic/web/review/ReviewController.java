package graduation.alcoholic.web.review;

import graduation.alcoholic.domain.entity.Image;
import graduation.alcoholic.web.S3.S3Service;
import graduation.alcoholic.web.image.ImageService;
import graduation.alcoholic.web.image.ReviewImageService;
import graduation.alcoholic.web.login.domain.jwt.JwtHeaderUtil;
import graduation.alcoholic.web.login.AuthService;
import graduation.alcoholic.web.review.dto.ReviewResponseDto;
import graduation.alcoholic.web.review.dto.ReviewSaveRequestDto;
import graduation.alcoholic.web.review.dto.ReviewTotalResponseDto;
import graduation.alcoholic.web.review.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthService authService;
    private final S3Service s3Service;
    private final ImageService imageService;
    private final ReviewImageService reviewImageService;

    @PostMapping(value = "/review")
    public Long save(HttpServletRequest httpRequest,
                     @RequestPart(value = "requestDto") ReviewSaveRequestDto requestDto, @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {

        String jwtToken = JwtHeaderUtil.getAccessToken(httpRequest);

        List<String> fileNameList = s3Service.uploadImage(fileList);
        List<Image> imageList = imageService.save(fileNameList);

        return reviewService.save(authService.getMemberId(jwtToken), requestDto, imageList);

    }

    @PutMapping("/review/{id}")
    public Long update(@PathVariable Long id, @RequestPart("requestDto") ReviewUpdateRequestDto requestDto, @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {

        s3Service.deleteImage(requestDto.getImageList());
        imageService.delete(requestDto.getImageList());

        List<String> fileNameList = s3Service.uploadImage(fileList);
        List<Image> imageList = imageService.save(fileNameList);

        return reviewService.update(id, requestDto, imageList);
    }


    @DeleteMapping("/review/{id}")
    public Long delete(@PathVariable Long id) {

        List<String> fileNameList = reviewService.delete(id);

        s3Service.deleteImage(fileNameList);
        imageService.delete(fileNameList);

        return id;
    }


    @GetMapping("/review/alcohol/{alcohol_id}")
    public ReviewTotalResponseDto findByAlcohol(@PathVariable Long alcohol_id) {

        return reviewService.findByAlcohol(alcohol_id);
    }


    @GetMapping("/review/user")
    public List<ReviewResponseDto> findByUser(HttpServletRequest httpRequest){

            String jwtToken = JwtHeaderUtil.getAccessToken(httpRequest);
            return reviewService.findByUser(authService.getMemberId(jwtToken));
    }




}
