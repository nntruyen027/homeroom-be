package com.vinhthanh2.lophocdientu.controller;

import com.vinhthanh2.lophocdientu.dto.req.LoginReq;
import com.vinhthanh2.lophocdientu.dto.req.TeacherRegisterReq;
import com.vinhthanh2.lophocdientu.dto.req.UpdatePassReq;
import com.vinhthanh2.lophocdientu.dto.res.LoginRes;
import com.vinhthanh2.lophocdientu.dto.res.UserFullRes;
import com.vinhthanh2.lophocdientu.exception.AppException;
import com.vinhthanh2.lophocdientu.mapper.UserMapper;
import com.vinhthanh2.lophocdientu.repository.UserRepo;
import com.vinhthanh2.lophocdientu.service.*;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Getter
@Setter
@AllArgsConstructor
@Tag(name = "Xác thực & Tài khoản", description = "Quản lý đăng nhập, đăng ký giáo viên và thông tin người dùng")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private AuthService authService;
    private final GiaoVienService giaoVienService;
    private final UserMapper userMapper;
    private final TinhService tinhService;
    private final XaService xaService;

    // -----------------------------------------------------------
    // LOGIN
    // -----------------------------------------------------------
    @Operation(
            summary = "Đăng nhập hệ thống",
            description = "Nhập username và password để lấy JWT token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Đăng nhập thành công, trả về token",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Sai username hoặc password", content = @Content)
    })
    @PostMapping("/login")
    public LoginRes login(@RequestBody LoginReq request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            String token = jwtService.generateToken(request.getUsername());
            UserFullRes user = userMapper.toUserFullDto(userRepository.findByUsername(request.getUsername()).get());
            return new LoginRes(token, user);

        } catch (BadCredentialsException ex) {
            throw new AppException("BAD_CREDENTIAL", "Thông tin đăng nhập không hợp lệ");
        }
    }

    // -----------------------------------------------------------
    // ĐĂNG KÝ GIÁO VIÊN
    // -----------------------------------------------------------
    @Operation(
            summary = "Đăng ký tài khoản giáo viên",
            description = "API dùng để tạo tài khoản giáo viên mới."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Đăng ký thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content)
    })
    @PostMapping("/dang-ky-giao-vien")
    public ResponseEntity<?> dangKyGiaoVien(@RequestBody TeacherRegisterReq req) {
        return ResponseEntity.ok(giaoVienService.dangKyGiaoVien(req));
    }

    // -----------------------------------------------------------
    // LẤY DANH SÁCH TỈNH
    // -----------------------------------------------------------
    @Operation(
            summary = "Lấy danh sách tỉnh",
            description = "API public để lấy danh sách tỉnh."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    })
    @GetMapping("/tinh")
    public ResponseEntity<?> layDsTinh(@RequestParam(required = false, defaultValue = "") String search,
                                       @RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(tinhService.layDsTinh(search, page, size));
    }

    // -----------------------------------------------------------
    // LẤY DANH SÁCH XÃ THEO TỈNH
    // -----------------------------------------------------------
    @Operation(
            summary = "Lấy danh sách xã theo tỉnh",
            description = "API public lấy danh sách xã dựa trên ID tỉnh."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    })
    @GetMapping("/tinh/{tinhId}/xa")
    public ResponseEntity<?> layDsXa(@RequestParam(required = false, defaultValue = "") String search,
                                     @PathVariable Long tinhId,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(xaService.layDsXa(search, tinhId, page, size));
    }

    // -----------------------------------------------------------
    // LẤY THÔNG TIN NGƯỜI DÙNG HIỆN TẠI
    // -----------------------------------------------------------
    @Operation(
            summary = "Xem thông tin user hiện tại",
            description = "Trả về thông tin user đang đăng nhập.",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
            @ApiResponse(responseCode = "401", description = "Không có hoặc token không hợp lệ", content = @Content)
    })
    @SecurityRequirement(name = "BearerAuth")
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUserDto());
    }

    // -----------------------------------------------------------
    // ĐỔI MẬT KHẨU
    // -----------------------------------------------------------
    @Operation(
            summary = "Đổi mật khẩu",
            description = "Yêu cầu người dùng phải đăng nhập.",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Đổi mật khẩu thành công"),
            @ApiResponse(responseCode = "400", description = "Mật khẩu cũ không đúng", content = @Content),
            @ApiResponse(responseCode = "401", description = "Không có hoặc token không hợp lệ", content = @Content)
    })
    @SecurityRequirement(name = "BearerAuth")
    @PutMapping("/doi-mat-khau")
    public ResponseEntity<?> doiMatKhau(@RequestBody UpdatePassReq updatePassReq) {
        authService.doiMatKhau(updatePassReq);
        return ResponseEntity.ok().build();
    }
}
