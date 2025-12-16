package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.FileReq;
import com.vinhthanh2.lophocdientu.dto.res.FileRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.repository.FileRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class FileService {

    private final FileRepo fileRepo;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Value("${server.public-url:http://localhost:8080}")
    private String publicUrl;

    // -----------------------
    // Upload file
    // -----------------------
    public FileRes upload(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
        Files.createDirectories(uploadPath);

        String storedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(storedName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        FileReq entity = FileReq.builder()
                .fileName(file.getOriginalFilename())
                .storedName(storedName)
                .url(publicUrl + "/files/public/" + storedName) // public URL
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();


        return fileRepo.luuFile(entity);
    }

    // -----------------------
    // Lấy danh sách file phân trang
    // -----------------------
    public PageResponse<FileRes> getAll(String search, int page, int size) {
        List<FileRes> fileRes = fileRepo
                .layTatCaFile(search, page, size)
                .stream()
                .toList();

        long totalElements = fileRepo.demTatCaFile(search);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<FileRes>builder()
                .data(fileRes)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    // -----------------------
    // Xóa file
    // -----------------------
    public void delete(Long id) throws IOException {
        fileRepo.xoaFile(id);
    }
}
