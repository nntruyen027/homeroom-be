package com.vinhthanh2.lophocdientu.config;

import com.vinhthanh2.lophocdientu.dto.req.AdminRequest;
import com.vinhthanh2.lophocdientu.dto.req.RoleReq;
import com.vinhthanh2.lophocdientu.entity.Role;
import com.vinhthanh2.lophocdientu.entity.User;
import com.vinhthanh2.lophocdientu.repository.PermissionRepo;
import com.vinhthanh2.lophocdientu.repository.RoleRepo;
import com.vinhthanh2.lophocdientu.repository.UserRepo;
import com.vinhthanh2.lophocdientu.service.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class DataInitializer {

    private static final String SUPER_ADMIN_CODE = "SUPER_ADMIN";
    private static final String SUPER_ADMIN_USERNAME = "superadmin";
    private static final String DEFAULT_PASSWORD = "Homeroom@2025";

    private static final List<String> DEFAULT_PERMISSIONS = List.of(
            "USER_MANAGE",
            "ROLE_MANAGE",
            "CLASS_MANAGE",
            "SCHOOL_MANAGE",
            "STUDENT_MANAGE",
            "TEACHER_MANAGE",
            "SYSTEM_CONFIG",
            "CATEGORY_MANAGE"
    );

    @Bean
    public CommandLineRunner initAdmin(
            UserRepo userRepo,
            AdminService adminService,
            PermissionRepo permissionRepo,
            RoleRepo roleRepo
    ) {
        return args -> {

            // 1️⃣ Tạo permission nếu chưa có
            DEFAULT_PERMISSIONS.forEach(code -> {
                if (!permissionRepo.coPermission(code)) {
                    permissionRepo.taoPermission(code);
                }
            });

            // 2️⃣ Tạo role SUPER_ADMIN nếu chưa có
            if (!roleRepo.coVaiTroTheoMa(SUPER_ADMIN_CODE)) {
                roleRepo.taoVaiTro(new RoleReq("Quản trị tối cao", SUPER_ADMIN_CODE));
            }

            // 3️⃣ Phân quyền cho role SUPER_ADMIN
            Role superAdminRole = roleRepo.timVaiTroTheoMa(SUPER_ADMIN_CODE);
            roleRepo.phanQuyenChoVaiTro(superAdminRole.getId(), DEFAULT_PERMISSIONS);

            // 4️⃣ Tạo user SUPER_ADMIN nếu chưa có
            Optional<User> userOpt = userRepo.findByUsername(SUPER_ADMIN_USERNAME);
            Long userId = userOpt.map(User::getId).orElseGet(() -> {
                AdminRequest adminRequest = new AdminRequest();
                adminRequest.setUserName(SUPER_ADMIN_USERNAME);
                adminRequest.setHoTen("Quản trị viên");
                adminRequest.setPassword(DEFAULT_PASSWORD);
                adminRequest.setRepeatPassword(DEFAULT_PASSWORD);

                return adminService.taoQuanTriVien(adminRequest).getId();
            });

            // 5️⃣ Gán role SUPER_ADMIN cho user
            userRepo.phanVaiTroChoNguoiDung(userId, List.of(SUPER_ADMIN_CODE));

            System.out.println("✅ SUPER_ADMIN initialized with all permissions.");
        };
    }
}
