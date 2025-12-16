package com.vinhthanh2.lophocdientu.controller.teacher;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.LopReq;
import com.vinhthanh2.lophocdientu.dto.res.LopRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.LopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/giao-vien/lop")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý lớp của giáo viên", description = "API cho giáo viên quản lý lớp học")
public class LopGiaoVienController {

    private final LopService lopService;
    private final AuthService authService;

    @Operation
            (summary = "Lấy danh sách lớp cá nhân"
            )
    @SecurityApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    })
    @GetMapping
    public PageResponse<LopRes> layDsLop(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        return lopService.layDsLopTheoGv(authService.getCurrentUser().getId(), search, page, limit);
    }


    // ============================
    // 2. Tạo lớp
    // ============================
    @Operation(
            summary = "Tạo lớp mới",
            description = "API cho phép giáo viên tạo lớp mới. ID giáo viên hiện tại tự động gán."
    )
    @SecurityApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tạo lớp thành công",
                    content = @Content(schema = @Schema(implementation = LopRes.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content)
    })
    @PostMapping
    public LopRes taoLop(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Thông tin lớp cần tạo",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LopReq.class))
            )
            @RequestBody LopReq lopReq
    ) {
        return lopService.taoLop(lopReq, authService.getCurrentUser().getId());
    }

    // ============================
    // 3. Sửa lớp
    // ============================
    @Operation(
            summary = "Sửa thông tin lớp",
            description = "API cho phép giáo viên sửa lớp mà mình tạo."
    )
    @SecurityApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cập nhật lớp thành công",
                    content = @Content(schema = @Schema(implementation = LopRes.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content)
    })
    @PutMapping("/{id}")
    public LopRes suaLop(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Thông tin lớp cần cập nhật",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LopReq.class))
            )
            @RequestBody LopReq lopReq
    ) {
        return lopService.suaLop(id, lopReq);
    }

    // ============================
    // 4. Xóa lớp
    // ============================
    @Operation(
            summary = "Xóa lớp",
            description = "API cho phép giáo viên xóa lớp do mình tạo."
    )
    @SecurityApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Xóa thành công")
    })
    @DeleteMapping("/{id}")
    public void xoaLop(@PathVariable Long id) {
        lopService.xoaLop(id);
    }
}
