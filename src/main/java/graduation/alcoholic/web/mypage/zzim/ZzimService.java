package graduation.alcoholic.web.mypage.zzim;

import graduation.alcoholic.domain.repository.AlcoholRepository;
import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.domain.entity.Zzim;
import graduation.alcoholic.domain.repository.ZzimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ZzimService {
    private final AlcoholRepository alcoholRepository;
    private final ZzimRepository zzimRepository;

    public HttpStatus addZzim (User user, Long a_id) {
        Alcohol alcohol = alcoholRepository.findById(a_id).get();
        boolean isRepeated = zzimRepository.findByUserIdAndAlcoholId(user.getId(), a_id).isEmpty();
        if(!isRepeated) {
            return HttpStatus.ALREADY_REPORTED;
        }else {
            Zzim zzim = new Zzim(user, alcohol);
            zzimRepository.save(zzim);
            return HttpStatus.OK;
        }
    }

    public List<Alcohol> getMyZzim (Long u_id) {
        List<Zzim> zzims = zzimRepository.findByUserId(u_id);
        List<Alcohol> res = new ArrayList<>();
        for (Zzim z : zzims) {
            res.add(z.getAlcohol());
        }
        return res;

    }

    @Transactional(readOnly = true)
    public HttpStatus deleteMyZzim (Long u_id,Long a_id) {
        if (this.findZzim(u_id, a_id)==true) {
            zzimRepository.deleteByUserIdAndAlcoholId(u_id, a_id);
             return HttpStatus.OK;
        }
        else return HttpStatus.NO_CONTENT;
    }

    public boolean findZzim (Long u_id, Long a_id) {
         Optional<Zzim> isZzimed = zzimRepository.findByUserIdAndAlcoholId(u_id, a_id);
        if (isZzimed.isPresent()) {
            return true;
        }
        else return false;
    }
}
