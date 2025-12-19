package com.vinhthanh2.lophocdientu.controller.teacher;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.HdHuongNghiepReq;
import com.vinhthanh2.lophocdientu.dto.res.HdHuongNghiepRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.HdHuongNghiepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giao-vien/hoat-dong-huong-nghiep")
@Getter
@Setter
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Tag(
        name = "Quản lý hoạt động hướng nghiệp của giáo viên",
        description = "API cho giáo viên tạo, cập nhật, xoá và xem danh sách hoạt động hướng nghiệp"
)
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
public class HdHuongNghiepController {

    private final HdHuongNghiepService hdHuongNghiepService;
    private final AuthService authService;

    /* =========================
       LẤY DS HOẠT ĐỘNG
       ========================= */
    @Operation(
            summary = "Lấy danh sách hoạt động hướng nghiệp",
            description = "Giáo viên lấy danh sách các hoạt động hướng nghiệp do mình tạo, có tìm kiếm và phân trang"
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
        return hdHuongNghiepService.layDsHdHuongNghiep(
                authService.getCurrentUser().getId(),
                search,
                page,
                limit
        );
    }

    /* =========================
       TẠO HOẠT ĐỘNG
       ========================= */
    @Operation(
            summary = "Tạo hoạt động hướng nghiệp",
            description = "Giáo viên tạo mới một hoạt động hướng nghiệp và phân công cho các lớp"
    )
    @SecurityApiResponses
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Tạo hoạt động hướng nghiệp thành công"
    )
    @PostMapping("")
    public HdHuongNghiepRes taoHoatDong(
            @RequestBody HdHuongNghiepReq req
    ) {
        return hdHuongNghiepService.taoHdHuongNghiep(req);
    }

    /* =========================
       SỬA HOẠT ĐỘNG
       ========================= */
    @Operation(
            summary = "Cập nhật hoạt động hướng nghiệp",
            description = "Giáo viên cập nhật thông tin hoạt động hướng nghiệp theo ID"
    )
    @SecurityApiResponses
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Cập nhật hoạt động hướng nghiệp thành công"
    )
    @PutMapping("/{id}")
    public HdHuongNghiepRes suaHoatDong(
            @PathVariable Long id,
            @RequestBody HdHuongNghiepReq req
    ) {
        return hdHuongNghiepService.suaHdHuongNghiep(id, req);
    }

    /* =========================
       XOÁ HOẠT ĐỘNG
       ========================= */
    @Operation(
            summary = "Xoá hoạt động hướng nghiệp",
            description = "Giáo viên xoá hoạt động hướng nghiệp theo ID"
    )
    @SecurityApiResponses
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Xoá hoạt động hướng nghiệp thành công"
    )
    @DeleteMapping("/{id}")
    public void xoaHoatDong(
            @PathVariable Long id
    ) {
        hdHuongNghiepService.xoaHdHuongNghiep(id);
    }

    @Operation(
            summary = "Phân hoạt động hướng nghiệp cho lớp",
            description = "Giáo viên phân hoạt động hướng nghiệp cho lơp"
    )
    @SecurityApiResponses
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Phân hoạt động hướng nghiệp cho lớp thành công"
    )
    @PostMapping("/{id}/lop")
    public HdHuongNghiepRes phanHoatDongChoLop(
            @PathVariable Long id,
            @RequestBody List<Long> lopIds
    ) {
        return hdHuongNghiepService.phanHoatDongChoLop(id, lopIds);
    }


}
