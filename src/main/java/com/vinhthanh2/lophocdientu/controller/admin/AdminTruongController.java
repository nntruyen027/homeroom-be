package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.TruongReq;
import com.vinhthanh2.lophocdientu.dto.res.LopRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.TeacherRes;
import com.vinhthanh2.lophocdientu.dto.res.TruongRes;
import com.vinhthanh2.lophocdientu.entity.Xa;
import com.vinhthanh2.lophocdientu.repository.XaRepo;
import com.vinhthanh2.lophocdientu.service.GiaoVienService;
import com.vinhthanh2.lophocdientu.service.LopService;
import com.vinhthanh2.lophocdientu.service.TruongService;
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
import org.springframework.core.io.Resource;
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
@RequestMapping("/quan-tri/truong")
@Getter
@Setter
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Quản lý trường dành cho Admin")
public class AdminTruongController {

    private final TruongService truongService;
    private final LopService lopService;
    private final GiaoVienService giaoVienService;
    private final XaRepo xaRepo;

    // ============================
    // 1. Lấy DS trường
    // ============================
    @Operation(summary = "Lấy danh sách trường",
            description = "API trả về danh sách trường có phân trang và tìm kiếm.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping
    public ResponseEntity<PageResponse<TruongRes>> layDsTruong(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(truongService.layDsTruong(search, page, size));
    }

    // ============================
    // 2. Lấy DS lớp thuộc trường
    // ============================
    @Operation(summary = "Lấy danh sách lớp thuộc trường",
            description = "API trả về danh sách lớp theo trường, có tìm kiếm + phân trang.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/{id}/lop")
    public ResponseEntity<PageResponse<LopRes>> layDsLopCuaTruong(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(lopService.layDsLopTheoTruong(id, search, page, size));
    }

    // ============================
    // 3. Lấy DS giáo viên theo trường
    // ============================
    @Operation(summary = "Lấy danh sách giáo viên thuộc trường",
            description = "API trả về danh sách giáo viên theo trường, có tìm kiếm + phân trang.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping("/{id}/giao-vien")
    public ResponseEntity<PageResponse<TeacherRes>> layDsGiaoVienThuocTruong(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(giaoVienService.layGiaoVienTheoTruong(id, search, page, size));
    }

    // ============================
    // 4. Tạo trường
    // ============================
    @Operation(summary = "Tạo trường", description = "API tạo mới một trường.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tạo thành công")
    @PostMapping
    public ResponseEntity<TruongRes> taoTruong(@RequestBody TruongReq truongReq) {
        return ResponseEntity.ok(truongService.taoTruong(truongReq));
    }

    // ============================
    // 5. Sửa trường
    // ============================
    @Operation(summary = "Sửa trường theo ID", description = "API sửa thông tin trường theo ID.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Sửa thành công")
    @PutMapping("/{id}")
    public ResponseEntity<TruongRes> suaTruong(@PathVariable Long id, @RequestBody TruongReq truongReq) {
        return ResponseEntity.ok(truongService.suaTruong(id, truongReq));
    }

    // ============================
    // 6. Xóa trường
    // ============================
    @Operation(summary = "Xóa trường theo ID",
            description = "API xóa trường theo ID.")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Xóa thành công")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaTruong(@PathVariable Long id) {
        truongService.xoaTruong(id);
        return ResponseEntity.ok().build();
    }

    // ============================
    // 7. Tải mẫu import trường
    // ============================
    @Operation(
            summary = "Tải file mẫu nhập trường theo tỉnh",
            description = "Trả về file Excel mẫu chứa danh sách xã theo tỉnh để nhập thông tin trường."
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Tải thành công", content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
    @GetMapping("/importer/template/tinh/{tinhId}")
    public ResponseEntity<byte[]> downloadTemplate(@PathVariable Long tinhId) throws IOException {

        Resource resource = new ClassPathResource("templates/mau_import_truong.xlsx");
        InputStream inputStream = resource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        List<Xa> xas = xaRepo.layTatCaXa("", tinhId, 1, 100000);

        XSSFSheet sheetXa = workbook.getSheet("dm_xa");
        if (sheetXa == null) sheetXa = workbook.createSheet("dm_xa");

        int lastRow = sheetXa.getLastRowNum();
        for (int i = lastRow; i > 0; i--) {
            Row row = sheetXa.getRow(i);
            if (row != null) sheetXa.removeRow(row);
        }

        Row header = sheetXa.getRow(0);
        if (header == null) header = sheetXa.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Tên xã");

        int rowIndex = 1;
        for (Xa xa : xas) {
            Row row = sheetXa.createRow(rowIndex++);
            row.createCell(0).setCellValue(xa.getId());
            row.createCell(1).setCellValue(xa.getId() + " - " + xa.getTen());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mau_import_truong.xlsx")
                .body(out.toByteArray());
    }

    // ============================
    // 8. Import trường bằng Excel
    // ============================
    @Operation(
            summary = "Import danh sách trường từ file Excel",
            description = "API cho phép tải lên file Excel để thêm danh sách trường."
    )
    @SecurityApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Import thành công"),
            @ApiResponse(responseCode = "400", description = "File không hợp lệ", content = @Content)
    })
    @PostMapping(value = "/importer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importTruong(@RequestParam("file") MultipartFile file) throws IOException {
        truongService.importTruong(file);
        return ResponseEntity.ok().build();
    }

}
