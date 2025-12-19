package com.vinhthanh2.lophocdientu.controller.student;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.res.HdHuongNghiepRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.HdHuongNghiepService;
import com.vinhthanh2.lophocdientu.service.HocSinhService;
import com.vinhthanh2.lophocdientu.service.LopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hoc-sinh/hoat-dong-huong-nghiep")
@Getter
@Setter
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(
        name = "Quản lý hoạt động hướng nghiệp của giáo viên",
        description = "API cho giáo viên tạo, cập nhật, xoá và xem danh sách hoạt động hướng nghiệp"
)
@PreAuthorize("hasRole('STUDENT')")
public class HsHdHuongNghiepController {

    private final HdHuongNghiepService hdHuongNghiepService;
    private final AuthService authService;
    private final UserDetailsService userDetailsService;
    private final LopService lopService;
    private final HocSinhService hocSinhService;

    /* =========================
       LẤY DS HOẠT ĐỘNG
       ========================= */
    @Operation(
            summary = "Lấy danh sách hoạt động hướng nghiệp",
            description = "Học sinh lấy danh sách các hoạt động hướng nghiệp được phân công, có tìm kiếm và phân trang"
    )
    @SecurityApiResponses
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Lấy danh sách hoạt động hướng nghiệp thành công"
    )
    @GetMapping("")
    public PageResponse<HdHuongNghiepRes> layTatCaHoatDong(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {

        return hdHuongNghiepService.layDsHdHuongNghiepTheoHs(
                authService.getCurrentUser().getId(),
                search,
                page,
                limit
        );
    }


}
