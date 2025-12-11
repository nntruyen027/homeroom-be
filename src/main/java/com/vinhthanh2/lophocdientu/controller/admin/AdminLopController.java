package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.LopReq;
import com.vinhthanh2.lophocdientu.dto.res.LopRes;
import com.vinhthanh2.lophocdientu.service.LopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quan-tri/lop")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý lớp dành cho Admin")
public class AdminLopController {

    private final LopService lopService;


    // ========================================
    // 1. Sửa lớp theo ID
    // ========================================
    @Operation(
            summary = "Sửa lớp theo ID",
            description = "API cho phép admin cập nhật thông tin lớp theo ID."
    )
    @SecurityApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sửa thành công"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<LopRes> suaLop(
            @PathVariable Long id,
            @RequestBody LopReq lopReq
    ) {
        return ResponseEntity.ok(lopService.suaLop(id, lopReq));
    }

    // ========================================
    // 2. Xóa lớp theo ID
    // ========================================
    @Operation(
            summary = "Xóa lớp theo ID",
            description = "API xóa một lớp theo ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Xóa thành công", content = @Content),
    })
    @SecurityApiResponses
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaLop(@PathVariable Long id) {
        lopService.xoaLop(id);
        return ResponseEntity.ok().build();
    }
}
