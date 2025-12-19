package com.vinhthanh2.lophocdientu.controller.student;

import com.vinhthanh2.lophocdientu.config.SecurityApiResponses;
import com.vinhthanh2.lophocdientu.dto.req.UpdateStudentReq;
import com.vinhthanh2.lophocdientu.dto.res.HocSinhRes;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.HocSinhService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("hoc-sinh/")
@Getter
@Setter
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Quản lý học sinh", description = "Các API quản lý thông tin học sinh")
public class HocSinhController {

    private final HocSinhService hocSinhService;
    private final AuthService authService;

    @Operation(
            summary = "Sửa thông tin cá nhân của học sinh",
            description = "API cho phép Admin hoặc Giáo viên chỉnh sửa thông tin cá nhân của học sinh."
    )
    @SecurityApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cập nhật thông tin thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content),
    })
    @PutMapping("")
    public ResponseEntity<HocSinhRes> suaThongTinCaNhan(
            @RequestBody UpdateStudentReq updateStudentReq
    ) {
        return ResponseEntity.ok(hocSinhService.suaThongTinCaNhan(updateStudentReq));
    }

}
