package com.vinhthanh2.lophocdientu.controller.student;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.KetQuaHollandReq;
import com.vinhthanh2.lophocdientu.dto.res.KetQuaHollandRes;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.HollandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hoc-sinh/holland")
@Getter
@Setter
@AllArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý điểm holland", description = "API cho học sinh quản lý điểm holland")
public class HsHollandController {
    private final HollandService hollandService;
    private final AuthService authService;

    @Operation(
            summary = "Lấy danh sách kết quả holland theo học sinh",
            description = "Học sinh lấy danh sách kết quả holland thuộc một học sinh"
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Lấy danh sách kết quả holland thành công")
    @GetMapping("")
    public List<KetQuaHollandRes> layDsHollandTheoHs() {
        return hollandService.layDsHollandTheoHs(authService.getCurrentUser().getId());
    }


    @Operation(
            summary = "Thực hiện bài holland",
            description = "Học sinh thực hiện bài kiểm tra hollad"
    )
    @SecurityApiResponses
    @ApiResponse(responseCode = "200", description = "Làm bài thành công")
    @PostMapping("")
    public KetQuaHollandRes lamBai(@RequestBody KetQuaHollandReq req) {
        return hollandService.lamBai(authService.getCurrentUser().getId(), req);
    }
}
