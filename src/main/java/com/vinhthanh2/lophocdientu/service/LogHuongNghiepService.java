package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.LogHdHuongNghiepReq;
import com.vinhthanh2.lophocdientu.dto.res.LogHdHuongNghiepRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.repository.LogHuongNghiepRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LogHuongNghiepService {
    private final LogHuongNghiepRepo logHuongNghiepRepo;

    public PageResponse<LogHdHuongNghiepRes> layDsLog(Long userId, int page, int limit) {
        List<LogHdHuongNghiepRes> data = logHuongNghiepRepo.layDsLog(
                userId, page, limit
        );
        Long totalElements = logHuongNghiepRepo.demDsLog(userId);
        int totalPages = (int) Math.ceil((double) totalElements / (double) limit);

        return PageResponse.<LogHdHuongNghiepRes>builder()
                .data(data)
                .page(page)
                .size(limit)
                .totalPages(totalPages)
                .totalElements(totalElements).build();
    }

    public LogHdHuongNghiepRes layLog(Long userId, Long hdId) {
        return logHuongNghiepRepo.layLog(userId, hdId);
    }

    public LogHdHuongNghiepRes nhanXetLog(Long hdId, Long hsId, Long gvId, String nhanXet) {
        return logHuongNghiepRepo.nhanXetLog(hdId, hsId, gvId, nhanXet);
    }

    public LogHdHuongNghiepRes taoLog(Long hdId, Long hsId, LogHdHuongNghiepReq logHdHuongNghiepReq) {
        return logHuongNghiepRepo.taoLog(hdId, hsId, logHdHuongNghiepReq);
    }

}
