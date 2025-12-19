package com.vinhthanh2.lophocdientu.controller.student;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.ThongBaoViewRes;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.HocSinhService;
import com.vinhthanh2.lophocdientu.service.ThongBaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hoc-sinh/thong-bao")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
@Tag(name = "Thông báo cho học sinh")
@SecurityRequirement(name = "BearerAuth")
public class ThongBaoController {
    private final ThongBaoService thongBaoService;
    private final AuthService authService;
    private final HocSinhService hocSinhService;

    @Operation(summary = "Lấy danh sách thông báo",
            description = "API trả về danh sách thông báo có phân trang và tìm kiếm.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping()
    public PageResponse<ThongBaoViewRes> layTatCaThongBao(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        Long hsId = authService.getCurrentUser().getId();
        return thongBaoService.layTatCaThongBao(
                hocSinhService.layHsTheoId(hsId).getLop().getId(), null, search, page, limit
        );
    }

    @Operation(summary = "Xem thông báo",
            description = "Xem thông báo ")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/{id}")
    public void xemThongBao(@PathVariable Long id) {
        thongBaoService.xemThongBao(id, authService.getCurrentUser().getId());
    }
}
