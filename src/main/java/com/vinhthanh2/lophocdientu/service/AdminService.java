package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.AdminRequest;
import com.vinhthanh2.lophocdientu.dto.req.UpdateAdminReq;
import com.vinhthanh2.lophocdientu.dto.res.AdminRes;
import com.vinhthanh2.lophocdientu.entity.User;
import com.vinhthanh2.lophocdientu.exception.AppException;
import com.vinhthanh2.lophocdientu.mapper.UserMapper;
import com.vinhthanh2.lophocdientu.repository.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepo adminRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public AdminRes taoQuanTriVien(AdminRequest adminRequest) {
        if (!Objects.equals(adminRequest.getPassword(), adminRequest.getRepeatPassword())) {
            throw new AppException("INVALID_PASSWORD", "Mật khẩu mới không khớp");
        }
        User admin = adminRepo.taoNguoiDungQuanTri(adminRequest.getUserName(), adminRequest.getHoTen(),
                passwordEncoder.encode(adminRequest.getPassword()), adminRequest.getAvatar());

        return userMapper.toAdminDto(admin);

    }

    public AdminRes suaThongTinCaNhan(UpdateAdminReq updateAdminReq) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() ||
                "anonymousUser".equals(auth.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        Optional<User> user = adminRepo.layQuanTriTheoUsername(auth.getName());

        return userMapper.toAdminDto(adminRepo.suaCaNhanAdmin(user.get().getId(), updateAdminReq));
    }

}
