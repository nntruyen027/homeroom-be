package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.XaReq;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.XaRes;
import com.vinhthanh2.lophocdientu.mapper.XaMapper;
import com.vinhthanh2.lophocdientu.repository.XaRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class XaService {
    private final XaRepo xaRepo;
    private final XaMapper xaMapper;

    public PageResponse<XaRes> layDsXa(String search, Long tinhId, int page, int size) {
        List<XaRes> xaList = xaRepo
                .layTatCaXa(search, tinhId, page, size)
                .stream()

                .toList();

        long totalElements = xaRepo.demTatCaXa(search, tinhId);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<XaRes>builder()
                .data(xaList)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    public XaRes taoXa(XaReq xaReq) {
        return (xaRepo.taoXa(xaReq));
    }

    public XaRes suaXa(Long id, XaReq xaReq) {
        return (xaRepo.suaXa(id, xaReq));
    }

    public void xoaXa(Long id) {
        xaRepo.xoaXa(id);
    }

    public void importXa(MultipartFile file) throws IOException {
        xaRepo.importXa(file);
    }
}
