package com.vinhthanh2.lophocdientu.controller.teacher;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.res.LopRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.TruongRes;
import com.vinhthanh2.lophocdientu.repository.XaRepo;
import com.vinhthanh2.lophocdientu.service.GiaoVienService;
import com.vinhthanh2.lophocdientu.service.LopService;
import com.vinhthanh2.lophocdientu.service.TruongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/giao-vien/truong")
@Getter
@Setter
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
@Tag(name = "Quản lý trường dành cho giáo viên")
public class GiaoVienTruongController {

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
}
