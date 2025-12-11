package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.UpdatePassAdminReq;
import com.vinhthanh2.lophocdientu.dto.req.UpdateStudentReq;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.StudentRes;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.HocSinhService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping("/quan-tri/hocsinh")
@Getter
@Setter
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý học sinh dành cho Admin")
public class AdminHocSinhController {

    private final HocSinhService hocSinhService;
    private final AuthService authService;

    // ============================================
    // 1. Lấy danh sách học sinh theo lớp
    // ============================================
    @Operation(
            summary = "Lấy danh sách học sinh theo lớp",
            description = "API trả về danh sách học sinh thuộc lớp theo phân trang và tìm kiếm theo tên."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công"),
    })
    @SecurityApiResponses
    @GetMapping("/lop/{lopId}")
    public ResponseEntity<PageResponse<StudentRes>> layDsHocSinhTheoLop(
            @PathVariable Long lopId,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(hocSinhService.layHocSinhTheoLop(lopId, search, page, size));
    }

    // ============================================
    // 2. Lấy học sinh theo ID
    // ============================================
    @Operation(
            summary = "Lấy học sinh theo ID",
            description = "API trả về chi tiết một học sinh theo ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy thành công")
    })
    @SecurityApiResponses
    @GetMapping("/{id}")
    public ResponseEntity<StudentRes> layHocSinhTheoId(@PathVariable Long id) {
        return ResponseEntity.ok(hocSinhService.layHocSinhTheoId(id));
    }

    // ============================================
    // 3. Sửa thông tin học sinh theo ID
    // ============================================
    @Operation(
            summary = "Sửa thông tin học sinh theo ID",
            description = "API cho phép Admin sửa thông tin của học sinh."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sửa thành công")
    })
    @SecurityApiResponses
    @PutMapping("/{id}")
    public ResponseEntity<StudentRes> suaHocSinh(
            @PathVariable Long id,
            @RequestBody UpdateStudentReq updateStudentReq) {

        return ResponseEntity.ok(hocSinhService.suaHocSinh(id, updateStudentReq));
    }

    // ============================================
    // 4. Reset mật khẩu học sinh theo ID
    // ============================================
    @Operation(
            summary = "Đặt lại mật khẩu học sinh theo ID",
            description = "API cho phép Admin đặt lại mật khẩu học sinh."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Đặt lại mật khẩu thành công")
    })
    @SecurityApiResponses
    @PutMapping("/password/{id}")
    public ResponseEntity<?> datLaiMatKhau(
            @PathVariable Long id,
            @RequestBody UpdatePassAdminReq updatePassReq) {

        authService.datLaiMatKhauBoiAdmin(id, updatePassReq.getNewPass());
        return ResponseEntity.ok().build();
    }

    // ============================================
    // 5. Xóa học sinh theo ID
    // ============================================
    @Operation(
            summary = "Xóa học sinh theo ID",
            description = "API xóa một học sinh theo ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Xóa thành công", content = @Content)
    })
    @SecurityApiResponses
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaHocSinh(@PathVariable Long id) {
        hocSinhService.xoaHocSinh(id);
        return ResponseEntity.ok().build();
    }
}
