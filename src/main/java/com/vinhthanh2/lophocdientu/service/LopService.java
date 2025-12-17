package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.LopReq;
import com.vinhthanh2.lophocdientu.dto.res.LopRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.UserFullRes;
import com.vinhthanh2.lophocdientu.mapper.LopMapper;
import com.vinhthanh2.lophocdientu.repository.LopRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LopService {
    private final LopRepo lopRepo;
    private final LopMapper lopMapper;
    private final AuthService authService;

    public PageResponse<LopRes> layDsLopTheoTruong(Long truongId, String search, int page, int size) {
        List<LopRes> truongList = lopRepo
                .layLopTheoTruong(truongId, search, page, size)
                .stream()
                .toList();

        long totalElements = lopRepo.demTatCaLopThuocTruong(truongId, search);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<LopRes>builder()
                .data(truongList)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }


    public PageResponse<LopRes> layDsLopTheoGv(Long giaoVienId, Long truongId, String search, int page, int size) {
        List<LopRes> truongList = lopRepo
                .layLopTheoGiaoVien(giaoVienId, truongId, search, page, size)
                .stream()
                .toList();

        long totalElements = lopRepo.demTatCaLopThuocGiaoVien(giaoVienId, truongId, search);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<LopRes>builder()
                .data(truongList)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    public LopRes taoLop(LopReq lopReq, Long giaoVienId) {
        return (lopRepo.taoLop(lopReq, giaoVienId));
    }

    public LopRes suaLop(Long id, LopReq lopReq) {
        UserFullRes user = authService.getCurrentUser();
        return (lopRepo.suaLop(id, lopReq, user.getId()));
    }

    public void xoaLop(Long id) {
        UserFullRes user = authService.getCurrentUser();
        lopRepo.xoaLop(id, user.getId());
    }
}
