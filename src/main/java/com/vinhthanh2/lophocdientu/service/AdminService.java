package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.AdminRequest;
import com.vinhthanh2.lophocdientu.dto.req.UpdateAdminReq;
import com.vinhthanh2.lophocdientu.dto.res.UserFullRes;
import com.vinhthanh2.lophocdientu.repository.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepo adminRepo;
    private final AuthService authService;

    public UserFullRes taoQuanTriVien(AdminRequest adminRequest) {
        return adminRepo.taoQuanTriVien(adminRequest);
    }

    public UserFullRes suaQuanTriVien(UpdateAdminReq adminRequest) {
        return adminRepo.capNhatQuanTriVien(authService.getCurrentUser().getId(), adminRequest);
    }
}
