package graduation.alcoholic.web.image;

import graduation.alcoholic.domain.entity.Image;
import graduation.alcoholic.domain.entity.Review;
import graduation.alcoholic.domain.repository.ImageRepository;
import graduation.alcoholic.web.S3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    @Transactional
    public List<Image> save(List<String> fileNameList) {

        List<Image> imageList = new ArrayList<>();
        for (String fileName : fileNameList) {
            Image image = new Image(fileName);
            imageList.add(image);
        }

        return imageRepository.saveAll(imageList);
    }


    @Transactional
    public void delete(List<String> fileNameList) {

        for (String fileName : fileNameList) {
            Image image = imageRepository.findByImage(fileName).orElseThrow(() -> new IllegalArgumentException("해당 이미지가 없습니다." + fileName));
            imageRepository.delete(image);
        }
    }

}
