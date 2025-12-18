package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.ThongBaoReq;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.ThongBaoViewRes;
import com.vinhthanh2.lophocdientu.repository.ThongBaoRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ThongBaoService {
    private final ThongBaoRepo thongBaoRepo;

    public PageResponse<ThongBaoViewRes> layTatCaThongBao(Long lopId, Long nguoiTaoId, String search, int page, int limit) {
        List<ThongBaoViewRes> data = thongBaoRepo.layTatCaThongBao(
                lopId, nguoiTaoId, search, page, limit
        );

        Long totalElements = thongBaoRepo.demTatCaThongBap(
                lopId, nguoiTaoId, search
        );

        int totalPages = (int) Math.ceil((double) totalElements / (double) limit);
        return PageResponse.<ThongBaoViewRes>builder()
                .data(data)
                .page(page)
                .size(limit)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();

    }

    public ThongBaoViewRes taoThongBao(Long lopId, Long nguoiTaoId, ThongBaoReq thongBaoReq) {
        return thongBaoRepo.taoThongBao(lopId, nguoiTaoId, thongBaoReq);
    }

    public ThongBaoViewRes suaThongBao(Long id, ThongBaoReq thongBaoReq) {
        return thongBaoRepo.suaThongBao(id, thongBaoReq);
    }

    public void xoaThongBao(Long id) {
        thongBaoRepo.xoaThongBao(id);
    }

    public void xemThongBao(Long thongBaoId, Long hsId) {
        thongBaoRepo.xemThongBao(thongBaoId, hsId);
    }
}
