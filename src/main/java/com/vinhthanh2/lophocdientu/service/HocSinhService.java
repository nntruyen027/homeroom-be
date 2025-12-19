package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.StudentRegisterReq;
import com.vinhthanh2.lophocdientu.dto.req.UpdateStudentReq;
import com.vinhthanh2.lophocdientu.dto.res.HocSinhRes;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.UserFullRes;
import com.vinhthanh2.lophocdientu.mapper.HocSinhMapper;
import com.vinhthanh2.lophocdientu.repository.HocSinhRepo;
import com.vinhthanh2.lophocdientu.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HocSinhService {
    private final HocSinhRepo hocSinhRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final HocSinhMapper hocSinhMapper;

    public PageResponse<HocSinhRes> layHocSinhTheoLop(Long lopId, String search, int page, int size) {
        List<HocSinhRes> teacherResList = hocSinhRepo
                .layTatCaHocSinh(lopId, search, page, size)
                .stream()
                .toList();

        long totalElements = hocSinhRepo.demHocSinhTheoLop(lopId, search);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<HocSinhRes>builder()
                .data(teacherResList)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    public HocSinhRes layHsTheoId(Long userId) {
        return hocSinhRepo.layHsTheoId(userId);
    }

    public HocSinhRes taoHocSinh(StudentRegisterReq req) {
        req.setPassword(passwordEncoder.encode(req.getPassword()));
        return (hocSinhRepo.taoHocSinh(req));
    }

    public HocSinhRes suaHocSinh(Long id, UpdateStudentReq req) {
        return (hocSinhRepo.suaHocSinh(id, req));
    }

    public HocSinhRes suaThongTinCaNhan(UpdateStudentReq updateStudentReq) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() ||
                "anonymousUser".equals(auth.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        UserFullRes user = userRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        return hocSinhRepo.suaThongTinCaNhan(user.getId(), updateStudentReq);
    }

    public void xoaHocSinh(Long id) {
        hocSinhRepo.xoaHocSinh(id);
    }


    public void importHocSinh(Long id, MultipartFile file) throws IOException {
        hocSinhRepo.importHocSinh(id, file);
    }
}
