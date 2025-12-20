package com.vinhthanh2.lophocdientu.controller.teacher;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.res.KetQuaHollandRes;
import com.vinhthanh2.lophocdientu.dto.res.ThongKeHollandTheoLopRes;
import com.vinhthanh2.lophocdientu.service.HollandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/giao-vien/holland")
@Getter
@Setter
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý điểm holland", description = "API cho giáo viên quản lý điểm holland")
public class GvHollandController {
    private final HollandService hollandService;


    @Operation(
            summary = "Lấy danh sách kết quả holland theo lớp",
            description = "Giáo viên/Admin lấy danh sách kết quả holland thuộc một lớp"
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách kết quả holland thành công")
    @GetMapping("/lop/{lopId}")
    public List<KetQuaHollandRes> layDsHollandTheoLop(@PathVariable Long lopId) {
        return hollandService.layDsHollandTheoLop(lopId);
    }

    @Operation(
            summary = "Lấy danh sách kết quả holland theo học sinh",
            description = "Giáo viên/Admin lấy danh sách kết quả holland thuộc một học sinh"
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách kết quả holland thành công")
    @GetMapping("/hoc-sinh/{hsId}")
    public List<KetQuaHollandRes> layDsHollandTheoHs(@PathVariable Long hsId) {
        return hollandService.layDsHollandTheoHs(hsId);
    }

    @Operation(
            summary = "Thống kê holland theo lớp",
            description = "Giáo viên/Admin thống kê kết quả holland thuộc một lớp"
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Thống kê holland thành công")
    @GetMapping("/thong-ke/lop/{lopId}")
    public ThongKeHollandTheoLopRes thongKeHollandTheoLop(@PathVariable Long lopId) {
        return hollandService.thongKeHolland(lopId);
    }
}
