package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.TinhReq;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.TinhRes;
import com.vinhthanh2.lophocdientu.mapper.TinhMapper;
import com.vinhthanh2.lophocdientu.repository.TinhRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class TinhService {
    private final TinhRepo tinhRepo;
    private final TinhMapper tinhMapper;

    public PageResponse<TinhRes> layDsTinh(String search, int page, int size) {
        List<TinhRes> tinhList = tinhRepo
                .layTatCaTinh(search, page, size)
                .stream()
                .toList();

        long totalElements = tinhRepo.demTatCaTinh(search);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<TinhRes>builder()
                .data(tinhList)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    public TinhRes taoTinh(TinhReq tinhReq) {
        return (tinhRepo.taoTinh(tinhReq));
    }

    public TinhRes suaTinh(Long id, TinhReq tinhReq) {
        return (tinhRepo.suaTinh(id, tinhReq));
    }

    public void xoaTinh(Long id) {
        tinhRepo.xoaTinh(id);
    }

    public void importTinh(MultipartFile file) throws IOException {
        tinhRepo.importTinh(file);
    }
}
