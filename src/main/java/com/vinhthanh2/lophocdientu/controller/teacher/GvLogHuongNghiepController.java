package com.vinhthanh2.lophocdientu.controller.teacher;


import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
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
@RequestMapping("/giao-vien/huong-nghiep/log")
@AllArgsConstructor
@PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
@Tag(name = "Quản lý nhật ký hướng nghiệp cho giáo viên")
@SecurityRequirement(name = "BearerAuth")
public class GvLogHuongNghiepController {
    private final LogHuongNghiepService logHuongNghiepService;
    private final AuthService authService;

    @Operation(summary = "Lấy danh sách nhật ký theo hoạt động",
            description = "API trả về danh sách nhật ký theo hoạt động có phân trang và tìm kiếm.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/{hdId}")
    PageResponse<LogHdHuongNghiepRes> layDsLogTheoHoatDong
            (@PathVariable Long hdId,
             @RequestParam(required = false) Long userId,
             @RequestParam(defaultValue = "1", required = false) int page,
             @RequestParam(defaultValue = "10", required = false) int limit) {
        return logHuongNghiepService.layDsLog(userId, hdId, page, limit);
    }

    @Operation(summary = "Nhận xét nhật ký",
            description = "API nhận xét nhật ký.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Nhận xét thành công")
    @PostMapping("/{hdId}/hoc-sinh/{userId}")
    LogHdHuongNghiepRes nhanXetLog(
            @PathVariable Long hdId,
            @PathVariable Long userId,
            @RequestBody String nhanXet
    ) {
        return logHuongNghiepService.nhanXetLog(hdId, userId, authService.getCurrentUser().getId(), nhanXet);
    }
}
