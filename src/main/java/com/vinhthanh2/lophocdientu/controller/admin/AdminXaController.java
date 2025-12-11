package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.XaReq;
import com.vinhthanh2.lophocdientu.entity.Tinh;
import com.vinhthanh2.lophocdientu.repository.TinhRepo;
import com.vinhthanh2.lophocdientu.service.XaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/quan-tri/xa")
@Getter
@Setter
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Quản lý xã dành cho Admin")
public class AdminXaController {

    private final XaService xaService;
    private final TinhRepo tinhRepo;

    // ============================
    // 1. Danh sách xã
    // ============================
    @Operation(
            summary = "Lấy danh sách xã",
            description = "API trả về danh sách xã theo phân trang, có hỗ trợ tìm kiếm và lọc theo tỉnh."
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping
    public ResponseEntity<?> layDsXa(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false) Long tinhId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(xaService.layDsXa(search, tinhId, page, size));
    }

    // ============================
    // 2. Tạo xã
    // ============================
    @Operation(
            summary = "Tạo xã mới",
            description = "API cho phép Admin tạo mới một xã."
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo xã thành công")
    @PostMapping
    public ResponseEntity<?> taoXa(@RequestBody XaReq xaReq) {
        return ResponseEntity.ok(xaService.taoXa(xaReq));
    }

    // ============================
    // 3. Sửa xã
    // ============================
    @Operation(
            summary = "Sửa thông tin xã",
            description = "API cho phép Admin cập nhật thông tin xã theo ID."
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Sửa xã thành công")
    @PutMapping("/{id}")
    public ResponseEntity<?> suaXa(
            @PathVariable Long id,
            @RequestBody XaReq xaReq) {

        return ResponseEntity.ok(xaService.suaXa(id, xaReq));
    }

    // ============================
    // 4. Xóa xã
    // ============================
    @Operation(
            summary = "Xóa xã theo ID",
            description = "API xóa xã theo ID."
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xóa xã thành công")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaXa(@PathVariable Long id) {
        xaService.xoaXa(id);
        return ResponseEntity.ok().build();
    }

    // ============================
    // 5. Tải file mẫu import xã
    // ============================
    @Operation(
            summary = "Tải file mẫu import xã",
            description = "Trả về file Excel mẫu dùng để nhập danh sách xã, bao gồm danh mục tỉnh."
    )
    @SecurityApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Tải file thành công",
                    content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
    })
    @GetMapping("/importer/template")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {

        ClassPathResource resource = new ClassPathResource("templates/mau_import_xa.xlsx");
        InputStream inputStream = resource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        List<Tinh> tinhs = tinhRepo.layTatCaTinh("", 1, 10000);

        XSSFSheet sheet = workbook.getSheet("DanhMucTinh");
        if (sheet == null) sheet = workbook.createSheet("DanhMucTinh");

        int lastRow = sheet.getLastRowNum();
        for (int i = lastRow; i > 0; i--) {
            Row row = sheet.getRow(i);
            if (row != null) sheet.removeRow(row);
        }

        Row header = sheet.getRow(0);
        if (header == null) header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Tên tỉnh");

        int rowIndex = 1;
        for (Tinh tinh : tinhs) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(tinh.getId());
            row.createCell(1).setCellValue(tinh.getId() + " - " + tinh.getTen());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mau_import_xa.xlsx")
                .body(out.toByteArray());
    }

    // ============================
    // 6. Import xã
    // ============================
    @Operation(
            summary = "Import danh sách xã từ Excel",
            description = "API cho phép tải lên file Excel để import danh sách xã."
    )
    @SecurityApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Import thành công"),
            @ApiResponse(responseCode = "400", description = "File không hợp lệ", content = @Content)
    })
    @PostMapping(value = "/importer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importXa(@RequestParam("file") MultipartFile file) throws IOException {
        xaService.importXa(file);
        return ResponseEntity.ok().build();
    }

}
