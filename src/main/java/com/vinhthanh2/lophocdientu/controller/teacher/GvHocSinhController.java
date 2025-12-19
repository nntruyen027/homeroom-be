package com.vinhthanh2.lophocdientu.controller.teacher;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.StudentRegisterReq;
import com.vinhthanh2.lophocdientu.dto.req.UpdateStudentReq;
import com.vinhthanh2.lophocdientu.dto.res.HocSinhRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.service.HocSinhService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/giao-vien/hoc-sinh")
@Getter
@Setter
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(
        name = "Quản lý học sinh của giáo viên",
        description = "API cho giáo viên quản lý thông tin học sinh theo lớp"
)
public class GvHocSinhController {

    private final HocSinhService hocSinhService;

    /* =========================
       LẤY DS HỌC SINH THEO LỚP
       ========================= */
    @Operation(
            summary = "Lấy danh sách học sinh theo lớp",
            description = "Giáo viên/Admin lấy danh sách học sinh thuộc một lớp, có phân trang và tìm kiếm"
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách học sinh thành công")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @GetMapping("/lop/{id}")
    public PageResponse<HocSinhRes> layHocSinhTheoLop(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        return hocSinhService.layHocSinhTheoLop(id, search, page, limit);
    }

    @Operation(
            summary = "Lấy học sinh theo id",
            description = "Giáo viên/Admin lấy học sinh"
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy học sinh thành công")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @GetMapping("/{id}")
    public HocSinhRes layHocSinhTheoId(@PathVariable Long id) {
        return hocSinhService.layHsTheoId(id);
    }

    /* =========================
       TẠO HỌC SINH
       ========================= */
    @Operation(
            summary = "Tạo mới học sinh",
            description = "Giáo viên/Admin tạo mới một học sinh (tạo user, profile, gán role STUDENT)"
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo học sinh thành công")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @PostMapping
    public HocSinhRes taoHocSinh(
            @RequestBody StudentRegisterReq studentRegisterReq
    ) {
        return hocSinhService.taoHocSinh(studentRegisterReq);
    }

    /* =========================
       CẬP NHẬT HỌC SINH
       ========================= */
    @Operation(
            summary = "Cập nhật thông tin học sinh",
            description = "Giáo viên/Admin cập nhật thông tin học sinh theo ID"
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cập nhật học sinh thành công")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @PutMapping("/{id}")
    public HocSinhRes suaHocSinh(
            @PathVariable Long id,
            @RequestBody UpdateStudentReq studentRegisterReq
    ) {
        return hocSinhService.suaHocSinh(id, studentRegisterReq);
    }

    /* =========================
       XOÁ HỌC SINH
       ========================= */
    @Operation(
            summary = "Xoá học sinh",
            description = "Giáo viên/Admin xoá học sinh theo ID"
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xoá học sinh thành công")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @DeleteMapping("/{id}")
    public void xoaHocSinh(
            @PathVariable Long id
    ) {
        hocSinhService.xoaHocSinh(id);
    }

    // 5) Tải template Excel
    @Operation(summary = "Tải xuống mẫu Excel import tỉnh")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tải xuống thành công")
    @GetMapping("/importer/template")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {

        byte[] bytes = new ClassPathResource("templates/mau_import_hoc_sinh.xlsx")
                .getInputStream().readAllBytes();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=mau_import_tinh.xlsx")
                .body(bytes);
    }

    // 6) Import Excel
    @Operation(summary = "Import danh sách tỉnh từ file Excel")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Import thành công", content = @Content)
    @PostMapping(value = "/lop/{id}/importer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importTinh(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        hocSinhService.importHocSinh(id, file);
        return ResponseEntity.ok().build();
    }
}
