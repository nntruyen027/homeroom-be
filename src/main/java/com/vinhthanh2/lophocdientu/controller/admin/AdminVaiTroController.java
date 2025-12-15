package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.PhanQuyenReq;
import com.vinhthanh2.lophocdientu.dto.req.RoleReq;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.RoleRes;
import com.vinhthanh2.lophocdientu.service.VaiTroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quan-tri/vai-tro")
@PreAuthorize("hasAuthority('ROLE_MANAGE')")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản trị vai trò dành cho quản trị viên")
public class AdminVaiTroController {
    private final VaiTroService roleService;

    @Operation(
            summary = "Lấy danh sách vai trò",
            description = "API trả về danh sách vai trò theo phân trang và tìm kiếm theo tên."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công"),
    })
    @SecurityApiResponses
    @GetMapping("")
    public PageResponse<RoleRes> layDsVaiTro(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return roleService.layTatCaVaiTro(search, page, limit);
    }

    @Operation(
            summary = "Tạo vai trò",
            description = "API tạo vai trò."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tạo thành công"),
    })
    @SecurityApiResponses
    @PutMapping("")
    public RoleRes taoVaiTro(@RequestBody RoleReq roleReq) {
        return roleService.taoVaiTro(roleReq);
    }

    @Operation(
            summary = "Sửa vai trò",
            description = "API sửa vai trò."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sửa thành công"),
    })
    @SecurityApiResponses
    @PutMapping("/{id}")
    public RoleRes suaVaiTro(@PathVariable Long id, @RequestBody RoleReq roleReq) {
        return roleService.suaVaiTro(id, roleReq);
    }


    @Operation(
            summary = "Phần quyền cho vai trò",
            description = "API phân quyền cho vai trò."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Phân quyền thành công"),
    })
    @SecurityApiResponses
    @PutMapping("/{id}/quyen")
    public RoleRes phanQuyenChoVaiTro(@PathVariable Long id, @RequestBody PhanQuyenReq phanQuyenReq) {
        return roleService.phanQuyenChoVaiTro(id, phanQuyenReq.getDsMaQuyen());
    }


    @Operation(
            summary = "Xóa vai trò",
            description = "API xóa vai trò."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Xóa thành công"),
    })
    @SecurityApiResponses
    @DeleteMapping("/{id}")
    public void xoaVaiTro(@PathVariable Long id) {
        roleService.xoaVaiTro(id);
    }
}
