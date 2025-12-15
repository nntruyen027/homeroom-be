package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.UpdateAdminReq;
import com.vinhthanh2.lophocdientu.dto.res.AdminRes;
import com.vinhthanh2.lophocdientu.service.AdminService;
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
@RequestMapping("/quan-tri")
@Getter
@Setter
@AllArgsConstructor
@PreAuthorize("hasAuthority('USER_MANAGE')")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý cá nhân", description = "Các API quản lý thông tin cá nhân admin")
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "Lấy danh sách quản trị")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @PutMapping("")
    public ResponseEntity<AdminRes> layDsQuanTri(
            @RequestParam String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(adminService);
    }

    @Operation(summary = "Sửa thông tin quản trị")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Sửa thông tin quản trị thành công")
    @PutMapping("")
    public ResponseEntity<AdminRes> suaThongTinCaNhan(
            @RequestBody UpdateAdminReq updateAdminReq
    ) {
        return ResponseEntity.ok(adminService.suaThongTinCaNhan(updateAdminReq));
    }
}
