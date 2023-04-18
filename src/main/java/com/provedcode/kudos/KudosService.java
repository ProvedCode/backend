package com.provedcode.kudos;

import com.provedcode.kudos.model.response.KudosAmount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KudosService {
    KudosRepository kudosRepository;

    public KudosAmount getAmountKudosProof(long id) {
        long count = kudosRepository.countByProof_Id(id);
        return new KudosAmount(count);
    }
}