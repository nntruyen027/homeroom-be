package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.KetQuaHollandReq;
import com.vinhthanh2.lophocdientu.dto.res.KetQuaHollandRes;
import com.vinhthanh2.lophocdientu.dto.res.ThongKeHollandTheoLopRes;
import com.vinhthanh2.lophocdientu.repository.HollandRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HollandService {
    private final HollandRepo hollandRepo;

    public List<KetQuaHollandRes> layDsHollandTheoLop(Long lopId) {
        return hollandRepo.layDsHollandTheoLop(lopId);
    }

    public List<KetQuaHollandRes> layDsHollandTheoHs(Long hsId) {
        return hollandRepo.layDsHollandTheoHs(hsId);
    }

    public ThongKeHollandTheoLopRes thongKeHolland(Long lopId) {
        return hollandRepo.thongKeHolland(lopId);
    }

    public KetQuaHollandRes lamBai(Long userId, KetQuaHollandReq req) {
        return hollandRepo.lamBai(userId, req);
    }
}
