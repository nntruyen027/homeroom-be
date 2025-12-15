package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.PermissionRes;
import com.vinhthanh2.lophocdientu.service.QuyenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quan-tri/quyen")
@PreAuthorize("hasAuthority('ROLE_MANAGE')")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản trị quyền dành cho quản trị viên")
public class AdminPermissionController {
    private final QuyenService quyenService;

    @Operation(
            summary = "Lấy danh sách quyền",
            description = "API trả về danh sách quyền theo phân trang và tìm kiếm theo tên."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công"),
    })
    @SecurityApiResponses
    @GetMapping("")
    public PageResponse<PermissionRes> layDsQuyen(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return quyenService.layTatCaQuyen(search, page, limit);
    }
}
