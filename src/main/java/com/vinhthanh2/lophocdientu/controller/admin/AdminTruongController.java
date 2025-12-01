package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.dto.req.TruongReq;
import com.vinhthanh2.lophocdientu.service.GiaoVienService;
import com.vinhthanh2.lophocdientu.service.LopService;
import com.vinhthanh2.lophocdientu.service.TruongService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/quan-tri/truong")
@Getter
@Setter
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminTruongController {
    final private TruongService truongService;
    final private LopService lopService;
    final private GiaoVienService giaoVienService;

    @GetMapping
    public ResponseEntity<?> layDsTruong(@RequestParam(required = false, defaultValue = "") String search,
                                         @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(truongService.layDsTruong(search, page, size));
    }

    @GetMapping("{id}/lop")
    public ResponseEntity<?> layDsLopCuaTruong(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(lopService.layDsLopTheoTruong(id, search, page, size));
    }

    @GetMapping("{id}/giao-vien")
    public ResponseEntity<?> layDsGiaoVienThuocTruong(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(giaoVienService.layGiaoVienTheoTruong(id, search, page, size));
    }

    @PostMapping
    public ResponseEntity<?> taoTruong(@RequestBody TruongReq truongReq) {
        return ResponseEntity.ok(truongService.taoTruong(truongReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> suaTruong(@PathVariable Long id, @RequestBody TruongReq truongReq) {
        return ResponseEntity.ok(truongService.suaTruong(id, truongReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaTruong(@PathVariable Long id) {
        truongService.xoaTruong(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/importer/template")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {
        // Lấy file từ resources
        Resource resource = new ClassPathResource("templates/mau_import_truong.xlsx");

        // Đọc nội dung file
        byte[] fileBytes = Files.readAllBytes(resource.getFile().toPath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mau_import_truong.xlsx")
                .body(fileBytes);
    }

    @PostMapping(value = "/importer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importTruong(@RequestParam("file") MultipartFile file) throws IOException {
        truongService.importTruong(file);
        return ResponseEntity.ok().build();
    }

}
