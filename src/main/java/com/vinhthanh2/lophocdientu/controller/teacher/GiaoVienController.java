package com.vinhthanh2.lophocdientu.controller.teacher;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.UpdateTeacherReq;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.GiaoVienService;
import com.vinhthanh2.lophocdientu.service.LopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/giao-vien")
@Getter
@Setter
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý giáo viên", description = "API cho giáo viên quản lý thông tin cá nhân")
public class GiaoVienController {

    private final GiaoVienService giaoVienService;
    private final AuthService authService;
    private final LopService lopService;

    // ============================
    // 1. Lấy giáo viên theo ID
    // ============================
    @Operation(
            summary = "Lấy thông tin giáo viên theo ID",
            description = "API trả về thông tin chi tiết giáo viên theo ID."
    )
    @SecurityApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy thành công"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> layGiaoVien(@PathVariable Long id) {
        return ResponseEntity.ok(giaoVienService.layGiaoVienTheoId(id));
    }

    // ============================
    // 2. Sửa thông tin cá nhân
    // ============================
    @Operation(
            summary = "Sửa thông tin cá nhân giáo viên",
            description = "API cho phép giáo viên hoặc Admin cập nhật thông tin cá nhân."
    )
    @SecurityApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content),
    })
    @PutMapping("")
    public ResponseEntity<?> suaThongTinCaNhan(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Thông tin cần cập nhật",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateTeacherReq.class))
            )
            UpdateTeacherReq updateTeacherReq
    ) {
        return ResponseEntity.ok(giaoVienService.suaThongTinCaNhan(updateTeacherReq));
    }
}
