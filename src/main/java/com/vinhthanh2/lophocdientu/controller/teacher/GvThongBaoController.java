package com.vinhthanh2.lophocdientu.controller.teacher;

import com.vinhthanh2.lophocdientu.dto.req.ThongBaoReq;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.ThongBaoViewRes;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.ThongBaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/giao-vien/thong-bao")
@AllArgsConstructor
@PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
@Tag(name = "Quản lý thông báo cho giáo viên")
@SecurityRequirement(name = "BearerAuth")
public class GvThongBaoController {
    private final ThongBaoService thongBaoService;
    private final AuthService authService;


    @GetMapping()
    PageResponse<ThongBaoViewRes> layTatCaThongBao(
            @RequestParam(required = false) Long lopId,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        return thongBaoService.layTatCaThongBao(
                lopId, authService.getCurrentUser().getId(), search, page, limit
        );
    }

    @PostMapping("/{lopId}")
    ThongBaoViewRes taoThongBao(@PathVariable Long lopId, @RequestBody ThongBaoReq thongBaoReq) {
        return thongBaoService.taoThongBao(lopId, authService.getCurrentUser().getId(), thongBaoReq);
    }

    @PutMapping("/{id}")
    ThongBaoViewRes suaThongBao(@PathVariable Long id, @RequestBody ThongBaoReq thongBaoReq) {
        return thongBaoService.suaThongBao(id, thongBaoReq);
    }

    @DeleteMapping("/{id}")
    void xoaThongBao(@PathVariable Long id) {
        thongBaoService.xoaThongBao(id);
    }
}
