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
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hoc-sinh/huong-nghiep/log")
@AllArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
@Tag(name = "Quản lý nhận ký hướng nghiệp cho học sinh")
@SecurityRequirement(name = "BearerAuth")
public class LogHuongNghiepController {
    private final LogHuongNghiepService logHuongNghiepService;
    private final AuthService authService;

    @Operation(summary = "Lấy danh sách nhật ký theo hoạt động",
            description = "API trả về danh sách nhật ký theo hoạt động có phân trang và tìm kiếm.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/{hdId}")
    private PageResponse<LogHdHuongNghiepRes> layDsLogTheoHoatDong
            (@PathVariable Long hdId,
             @RequestParam(defaultValue = "1", required = false) int page,
             @RequestParam(defaultValue = "10", required = false) int limit) {
        return logHuongNghiepService.layDsLog(authService.getCurrentUserDto().getId(), hdId, page, limit);
    }

    @Operation(summary = "Thêm nhật ký hướng nghiệp",
            description = "API thêm nhật ký hướng nghiệp.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Thêm thành công")
    @PostMapping("/{hdId}")
    private LogHdHuongNghiepRes themLog(
            @PathVariable Long hdId,
            @RequestBody LogHdHuongNghiepReq logHdHuongNghiepReq
    ) {
        return logHuongNghiepService.taoLog(hdId, authService.getCurrentUser().getId(), logHdHuongNghiepReq);
    }


    @Operation(summary = "Sửa nhật k",
            description = "API sửa nhật ký.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Sửa nhật ký thành công")
    @PutMapping("/{hdId}")
    private LogHdHuongNghiepRes suaLog
            (
                    @PathVariable Long hdId,
                    @RequestBody LogHdHuongNghiepReq logHdHuongNghiepReq
            ) {
        return logHuongNghiepService.suaLog(hdId, authService.getCurrentUser().getId(), logHdHuongNghiepReq);
    }

    @Operation(summary = "Xóa nhật ký",
            description = "API xóa nhật ký.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xóa thành công")
    @DeleteMapping("/{hdId}")
    private void xoaLog(
            @PathVariable Long hdId
    ) {
        logHuongNghiepService.xoaLog(hdId, authService.getCurrentUser().getId());
    }
}
