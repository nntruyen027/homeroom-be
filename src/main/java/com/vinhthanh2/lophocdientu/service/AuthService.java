package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.UpdatePassReq;
import com.vinhthanh2.lophocdientu.entity.User;
import com.vinhthanh2.lophocdientu.exception.AppException;
import com.vinhthanh2.lophocdientu.mapper.UserMapper;
import com.vinhthanh2.lophocdientu.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Object getCurrentUserDto() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() ||
                "anonymousUser".equals(auth.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        User user = userRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        String role = user.getRole().toUpperCase();

        if (role.contains("ADMIN")) return user;
        if (role.contains("STUDENT")) return userMapper.toStudentDto(user);

        return userMapper.toTeacherDto(user);
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() ||
                "anonymousUser".equals(auth.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        return userRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }


    public void doiMatKhau(UpdatePassReq updatePassReq) {
        String username = SecurityUtils.getCurrentUsername();

        if (username == null) {
            throw new AppException("Không tìm thấy thông tin người dùng đăng nhập", "USER_NOT_FOUND");
        }

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new AppException("Không tìm thấy người dùng", "USER_NOT_FOUND"));

        if (updatePassReq.getOldPass() == null || updatePassReq.getOldPass().isBlank()) {
            throw new AppException("Mật khẩu cũ không được để trống", "INVALID_PASSWORD");
        }

        if (!passwordEncoder.matches(updatePassReq.getOldPass(), user.getPassword())) {
            throw new AppException("Mật khẩu cũ không chính xác", "INVALID_PASSWORD");
        }

        if (updatePassReq.getNewPass() == null || updatePassReq.getNewPass().isBlank()) {
            throw new AppException("Mật khẩu mới không được để trống", "INVALID_PASSWORD");
        }

        if (!updatePassReq.getNewPass().equals(updatePassReq.getRepeatNewPass())) {
            throw new AppException("Mật khẩu mới không khớp", "INVALID_PASSWORD");
        }

        user.setPassword(passwordEncoder.encode(updatePassReq.getNewPass()));
        userRepo.save(user);
    }

    public void doiMatKhau(Long userId, UpdatePassReq updatePassReq) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException("Không tìm thấy người dùng", "USER_NOT_FOUND"));

        if (!passwordEncoder.matches(updatePassReq.getOldPass(), user.getPassword())) {
            throw new AppException("Mật khẩu cũ không chính xác", "INVALID_PASSWORD");
        }


        if (updatePassReq.getNewPass() != updatePassReq.getRepeatNewPass()) {
            throw new AppException("Mật khẩu mới không khớp", "INVALID_PASSWORD");
        }

        user.setPassword(passwordEncoder.encode(updatePassReq.getNewPass()));
        userRepo.save(user);
    }
}
