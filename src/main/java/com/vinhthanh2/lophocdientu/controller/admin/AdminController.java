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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quan-tri")
@Getter
@Setter
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý cá nhân admin", description = "Các API quản lý thông tin cá nhân admin")
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "Sửa thông tin quản tr")
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Sửa thông tin quản trị thành công")
    @PutMapping("")
    public ResponseEntity<AdminRes> suaThongTinCaNhan(
            @RequestBody UpdateAdminReq updateAdminReq
    ) {
        return ResponseEntity.ok(adminService.suaThongTinCaNhan(updateAdminReq));
    }
}
