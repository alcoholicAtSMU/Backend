package graduation.alcoholic.collection.collectionContent;

import graduation.alcoholic.collection.collectionInfo.CollectionInfoRepository;
import graduation.alcoholic.domain.CollectionContent;
import graduation.alcoholic.domain.CollectionContentId;
import graduation.alcoholic.domain.CollectionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CollectionContentService {

    private final CollectionContentRepository collectionContentRepository;
    private final CollectionInfoRepository collectionInfoRepository;

    @Transactional
    public CollectionContentId save(CollectionContentSaveRequestDto collectionContentSaveRequestDto) {
        return collectionContentRepository.save(collectionContentSaveRequestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<CollectionContentResponseDto> findByCollectionInfo(Long collection_id) {

        CollectionInfo collectionInfo = collectionInfoRepository.findById(collection_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 컬렉션이 없습니다. id: " + collection_id));

        return collectionContentRepository.findByCollectionInfo(collectionInfo);
    }

    @Transactional
    public void delete(CollectionContentId id) {

        CollectionContent collectionContent = collectionContentRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 컬렉션 컨텐츠가 없습니다. id: " + id));

        collectionContentRepository.delete(collectionContent);
    }
}
