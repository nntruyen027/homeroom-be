package com.vinhthanh2.lophocdientu.controller.student;


import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.LogHdHuongNghiepReq;
import com.vinhthanh2.lophocdientu.dto.res.LogHdHuongNghiepRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.LogHuongNghiepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hoc-sinh/huong-nghiep/log")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
@Tag(name = "Quản lý nhật ký hướng nghiệp cho học sinh")
@SecurityRequirement(name = "BearerAuth")
public class LogHuongNghiepController {
    private final LogHuongNghiepService logHuongNghiepService;
    private final AuthService authService;

    @Operation(summary = "Lấy danh sách nhật ký theo học sinh",
            description = "API trả về danh sách nhật ký theo học sinh có phân trang và tìm kiếm.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("")
    public PageResponse<LogHdHuongNghiepRes> layDsLogTheoHoatDong
            (
                    @RequestParam(defaultValue = "1", required = false) int page,
                    @RequestParam(defaultValue = "10", required = false) int limit) {
        return logHuongNghiepService.layDsLog(authService.getCurrentUser().getId(), page, limit);
    }

    @Operation(summary = "Lấy danh sách nhật ký theo học sinh theo hoạt động",
            description = "API trả về danh sách nhật ký theo học sinh theo hoạt động có phân trang và tìm kiếm.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/hoat-dong/{hdId}")
    public LogHdHuongNghiepRes layDsLogTheoHoatDong
            (@PathVariable Long hdId) {
        return logHuongNghiepService.layLog(authService.getCurrentUser().getId(), hdId);
    }

    @Operation(summary = "Thêm nhật ký hướng nghiệp",
            description = "API thêm nhật ký hướng nghiệp.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Thêm thành công")
    @PostMapping("/{hdId}")
    public LogHdHuongNghiepRes themLog(
            @PathVariable Long hdId,
            @RequestBody LogHdHuongNghiepReq logHdHuongNghiepReq
    ) {
        return logHuongNghiepService.taoLog(hdId, authService.getCurrentUser().getId(), logHdHuongNghiepReq);
    }
}
