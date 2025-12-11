package com.vinhthanh2.lophocdientu.controller;

import com.vinhthanh2.lophocdientu.dto.res.FileResponse;
import com.vinhthanh2.lophocdientu.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "Quản lý File", description = "Upload, download và quản lý file")
public class FileController {

    private final FileService fileService;

    // -------------------------------
    // Upload file
    // -------------------------------
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileService.upload(file));
    }

    // -------------------------------
    // Lấy danh sách file phân trang
    // -------------------------------
    @GetMapping
    public ResponseEntity<Page<FileResponse>> getAllFiles(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(fileService.getAll(page - 1, size));
    }

    // -------------------------------
    // Xóa file
    // -------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) throws IOException {
        fileService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------------------
    // Serve file public
    // -------------------------------
    @GetMapping("/public/{fileName:.+}") // .+ để lấy cả extension
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) throws IOException {
        Path path = Paths.get(fileService.getUploadDir()).toAbsolutePath().resolve(fileName);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(path);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                .body(resource);
    }
}
