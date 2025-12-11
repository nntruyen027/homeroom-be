package com.vinhthanh2.lophocdientu.controller;

import com.vinhthanh2.lophocdientu.dto.res.FileResponse;
import com.vinhthanh2.lophocdientu.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "Quản lý File", description = "Upload, download và quản lý file")
public class FileController {

    private final FileService fileService;

    // ---------------------------------------------
    // UPLOAD FILE
    // ---------------------------------------------
    @Operation(
            summary = "Upload file",
            description = "API dùng để upload 1 file lên server."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Upload thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class))),
            @ApiResponse(responseCode = "400", description = "File không hợp lệ", content = @Content)
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> uploadFile(
            @org.springframework.web.bind.annotation.RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileService.upload(file));
    }

    // ---------------------------------------------
    // LẤY DS FILE
    // ---------------------------------------------
    @Operation(
            summary = "Lấy danh sách toàn bộ file",
            description = "Trả về danh sách file đã upload."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<FileResponse>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAll());
    }

    // ---------------------------------------------
    // DELETE FILE
    // ---------------------------------------------
    @Operation(
            summary = "Xoá file theo ID",
            description = "API xoá file khỏi hệ thống."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Xoá thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy file", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) throws IOException {
        fileService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------------------------------------
    // SERVE FILE PUBLIC
    // ---------------------------------------------
    @Operation(
            summary = "Tải file public",
            description = "API public để tải file trực tiếp bằng tên file."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tải file thành công",
                    content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "404", description = "File không tồn tại", content = @Content)
    })
    @GetMapping("/public/{fileName}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) throws IOException {
        Path path = Paths.get("uploads").resolve(fileName);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(path);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        contentType != null ? contentType : "application/octet-stream"))
                .body(resource);
    }
}
