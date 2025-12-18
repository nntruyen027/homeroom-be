package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.HdHuongNghiepReq;
import com.vinhthanh2.lophocdientu.dto.res.HdHuongNghiepRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.repository.HdHuongNghiepRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HdHuongNghiepService {
    private final HdHuongNghiepRepo hdHuongNghiepRepo;
    private final AuthService authService;

    public PageResponse<HdHuongNghiepRes> layDsHdHuongNghiep(Long userId, String search, int page, int limit) {
        int offset = (page - 1) * limit;
        List<HdHuongNghiepRes> data = hdHuongNghiepRepo.layTatCaHoatDong(userId, search, offset, limit);
        Long totalElements = hdHuongNghiepRepo.demTatCaHoatDong(userId, search);
        int totalPages = (int) Math.ceil((double) totalElements / (double) limit);
        return PageResponse.<HdHuongNghiepRes>builder()
                .data(data)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .page(page)
                .size(limit)
                .build();
    }

    public HdHuongNghiepRes taoHdHuongNghiep(HdHuongNghiepReq hdHuongNghiepReq) {
        return hdHuongNghiepRepo.taoHoatDong(hdHuongNghiepReq, authService.getCurrentUser().getId());
    }

    public HdHuongNghiepRes suaHdHuongNghiep(Long id, HdHuongNghiepReq hdHuongNghiepReq) {
        return hdHuongNghiepRepo.suaHoatDong(id, hdHuongNghiepReq);
    }

    public void xoaHdHuongNghiep(Long id) {
        hdHuongNghiepRepo.xoaHoatDong(id);
    }

    public HdHuongNghiepRes phanHoatDongChoLop(Long id, List<Long> lopIds) {
        return hdHuongNghiepRepo.phanHoatDongChoLop(id, lopIds);
    }
}
