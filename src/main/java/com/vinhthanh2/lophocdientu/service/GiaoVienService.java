package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.TeacherRegisterReq;
import com.vinhthanh2.lophocdientu.dto.req.UpdateTeacherReq;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.TeacherRes;
import com.vinhthanh2.lophocdientu.dto.res.UserFullRes;
import com.vinhthanh2.lophocdientu.exception.AppException;
import com.vinhthanh2.lophocdientu.mapper.GiaoVienMapper;
import com.vinhthanh2.lophocdientu.repository.GiaoVienRepo;
import com.vinhthanh2.lophocdientu.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class GiaoVienService {
    private final GiaoVienRepo giaoVienRepo;
    private final GiaoVienMapper giaoVienMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public PageResponse<TeacherRes> layDsGiaoVien(String search, int page, int size) {
        List<TeacherRes> teacherResList = giaoVienRepo
                .layTatCaGiaoVien(search, page, size)
                .stream()
                .toList();

        long totalElements = giaoVienRepo.demTatCaGiaoVien(search);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<TeacherRes>builder()
                .data(teacherResList)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    public TeacherRes dangKyGiaoVien(TeacherRegisterReq req) {
        if (!Objects.equals(req.getPassword(), req.getRepeatPass())) {
            throw new AppException("INVALID_PASSWORD", "Mật khẩu không khớp");
        }
        req.setPassword(passwordEncoder.encode(req.getPassword()));
        return giaoVienRepo.taoGiaoVien((req));
    }

    public TeacherRes suaGiaoVien(Long id, UpdateTeacherReq req) {
        return (giaoVienRepo.suaGiaoVien(id, (req)));
    }

    public TeacherRes suaThongTinCaNhan(UpdateTeacherReq updateTeacherReq) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() ||
                "anonymousUser".equals(auth.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Không có quyền chỉnh chỉnh sửa");
        }

        UserFullRes user = userRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng"));


        return suaGiaoVien(user.getId(), updateTeacherReq);
    }

    public void xoaGiaoVien(Long id) {
        giaoVienRepo.xoaGiaoVien(id);
    }

}
