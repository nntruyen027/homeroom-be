package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.TinhReq;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.TinhRes;
import com.vinhthanh2.lophocdientu.service.TinhService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/quan-tri/tinh")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý danh mục tỉnh dành cho Admin")
public class AdminTinhController {

    private final TinhService tinhService;

    // 1) Lấy danh sách tỉnh
    @Operation(summary = "Lấy danh sách tỉnh (phân trang + tìm kiếm)")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping
    public ResponseEntity<PageResponse<TinhRes>> layDsTinh(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(tinhService.layDsTinh(search, page, size));
    }

    // 2) Tạo mới tỉnh
    @Operation(summary = "Tạo mới tỉnh")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo thành công")
    @PostMapping
    public ResponseEntity<TinhRes> taoTinh(@RequestBody TinhReq tinhReq) {
        return ResponseEntity.ok(tinhService.taoTinh(tinhReq));
    }

    // 3) Cập nhật tỉnh
    @Operation(summary = "Cập nhật tỉnh theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @PutMapping("/{id}")
    public ResponseEntity<TinhRes> suaTinh(@PathVariable Long id, @RequestBody TinhReq tinhReq) {
        return ResponseEntity.ok(tinhService.suaTinh(id, tinhReq));
    }

    // 4) Xoá tỉnh
    @Operation(summary = "Xoá tỉnh theo ID")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xoá thành công", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaTinh(@PathVariable Long id) {
        tinhService.xoaTinh(id);
        return ResponseEntity.ok().build();
    }

    // 5) Tải template Excel
    @Operation(summary = "Tải xuống mẫu Excel import tỉnh")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tải xuống thành công")
    @GetMapping("/importer/template")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {

        byte[] bytes = new ClassPathResource("templates/mau_import_tinh.xlsx")
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
    @PostMapping(value = "/importer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importTinh(@RequestParam("file") MultipartFile file) throws IOException {
        tinhService.importTinh(file);
        return ResponseEntity.ok().build();
    }
}
