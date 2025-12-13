package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.UpdatePassAdminReq;
import com.vinhthanh2.lophocdientu.dto.req.UpdateTeacherReq;
import com.vinhthanh2.lophocdientu.dto.res.LopRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.TeacherRes;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.GiaoVienService;
import com.vinhthanh2.lophocdientu.service.LopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quan-tri/giao-vien")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý giáo viên dành cho Admin")
public class AdminGiaoVienController {

    private final GiaoVienService giaoVienService;
    private final AuthService authService;
    private final LopService lopService;

    // ------------------------------------------
    // 1. Lấy danh sách giáo viên
    // ------------------------------------------
    @Operation(summary = "Lấy danh sách giáo viên")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping
    @PreAuthorize("hasAuthority('TEACHER_MANAGE')")
    public ResponseEntity<PageResponse<TeacherRes>> layDsGiaoVien(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(giaoVienService.layDsGiaoVien(search, page, size));
    }

    // ------------------------------------------
    // 2. Lấy danh sách lớp theo giáo viên
    // ------------------------------------------
    @Operation(summary = "Lấy danh sách lớp thuộc giáo viên")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/{id}/lop")
    @PreAuthorize("hasAuthority('CLASS_MANAGE')")
    public ResponseEntity<PageResponse<LopRes>> layDsLopThuocGiaoVien(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(lopService.layDsLopTheoGv(id, search, page, size));
    }

    // ------------------------------------------
    // 3. Lấy giáo viên theo ID
    // ------------------------------------------
    @PreAuthorize("hasAuthority('TEACHER_MANAGE')")
    @Operation(summary = "Lấy giáo viên theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy thành công")
    @GetMapping("/{id}")
    public ResponseEntity<TeacherRes> layGiaoVienTheoId(@PathVariable Long id) {
        return ResponseEntity.ok(giaoVienService.layGiaoVienTheoId(id));
    }

    // ------------------------------------------
    // 4. Sửa giáo viên theo ID
    // ------------------------------------------
    @PreAuthorize("hasAuthority('TEACHER_MANAGE')")
    @Operation(summary = "Sửa giáo viên theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Sửa thành công")
    @PutMapping("/{id}")
    public ResponseEntity<TeacherRes> suaGiaoVien(
            @PathVariable Long id,
            @RequestBody UpdateTeacherReq updateTeacherReq) {

        return ResponseEntity.ok(giaoVienService.suaGiaoVien(id, updateTeacherReq));
    }

    // ------------------------------------------
    // 5. Reset mật khẩu giáo viên
    // ------------------------------------------
    @PreAuthorize("hasAuthority('TEACHER_MANAGE')")
    @Operation(summary = "Đặt lại mật khẩu giáo viên")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cập nhật mật khẩu thành công", content = @Content)
    @PutMapping("/password/{id}")
    public ResponseEntity<?> datLaiMatKhau(
            @PathVariable Long id,
            @RequestBody UpdatePassAdminReq body) {

        authService.datLaiMatKhauBoiAdmin(id, body.getNewPass());
        return ResponseEntity.ok().build();
    }

    // ------------------------------------------
    // 6. Xóa giáo viên theo ID
    // ------------------------------------------
    @PreAuthorize("hasAuthority('TEACHER_MANAGE')")
    @Operation(summary = "Xóa giáo viên theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xóa thành công", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaGiaoVien(@PathVariable Long id) {
        giaoVienService.xoaGiaoVien(id);
        return ResponseEntity.ok().build();
    }
}
