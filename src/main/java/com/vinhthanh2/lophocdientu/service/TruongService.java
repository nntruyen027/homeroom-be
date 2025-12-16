package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.TruongReq;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.TruongRes;
import com.vinhthanh2.lophocdientu.mapper.TruongMapper;
import com.vinhthanh2.lophocdientu.repository.TruongRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class TruongService {
    private final TruongRepo truongRepo;
    private final TruongMapper truongMapper;

    public PageResponse<TruongRes> layDsTruong(String search, int page, int size) {
        List<TruongRes> truongList = truongRepo
                .layTatCaTruong(search, page, size)
                .stream()
                .toList();

        long totalElements = truongRepo.demTatCaTruong(search);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<TruongRes>builder()
                .data(truongList)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    public TruongRes taoTruong(TruongReq truongReq) {
        return (truongRepo.taoTruong(truongReq));
    }

    public TruongRes suaTruong(Long id, TruongReq truongReq) {
        return (truongRepo.suaTruong(id, truongReq));
    }

    public void xoaTruong(Long id) {
        truongRepo.xoaTruong(id);
    }

    public void importTruong(MultipartFile file) throws IOException {
        truongRepo.importTruong(file);
    }


}
