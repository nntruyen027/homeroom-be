package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.AdminRes;
import com.vinhthanh2.lophocdientu.dto.res.TeacherRes;
import com.vinhthanh2.lophocdientu.dto.res.UserFullRes;
import com.vinhthanh2.lophocdientu.dto.sql.AdminPro;
import com.vinhthanh2.lophocdientu.dto.sql.GiaoVienPro;
import com.vinhthanh2.lophocdientu.dto.sql.UserFullPro;
import com.vinhthanh2.lophocdientu.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                XaMapper.class,
                TinhMapper.class,
                LopMapper.class,
                TruongMapper.class
        }
)
public interface UserMapper {

    /* =======================
       ENTITY → RESPONSE DTO
       ======================= */

    TeacherRes toTeacherDto(User user);

    AdminRes toAdminDto(User user);

    UserFullRes toUserFullDto(User user);

    /* =======================
       PROJECTION → ENTITY
       ======================= */

    /* ===== Giáo viên ===== */
    @Mapping(source = "outId", target = "id")

    // DB id
    @Mapping(source = "xaId", target = "xaId")

    // Object graph
    @Mapping(source = "xaId", target = "xa.id")
    @Mapping(source = "tenXa", target = "xa.ten")
    @Mapping(source = "tinhId", target = "xa.tinh.id")
    @Mapping(source = "tenTinh", target = "xa.tinh.ten")
    User fromGiaoVienPro(GiaoVienPro dto);


    /* ===== Admin ===== */
    @Mapping(source = "outId", target = "id")
    User fromAdminPro(AdminPro dto);


    /* ===== User đầy đủ (auth.v_users_full) ===== */
    @Mapping(source = "outId", target = "id")

    // ===== DB columns =====
    @Mapping(source = "xaId", target = "xaId")
    @Mapping(source = "lopId", target = "lopId")

    // ===== XA + TỈNH =====
    @Mapping(source = "xaId", target = "xa.id")
    @Mapping(source = "tenXa", target = "xa.ten")
    @Mapping(source = "tinhId", target = "xa.tinh.id")
    @Mapping(source = "tenTinh", target = "xa.tinh.ten")

    // ===== LỚP + TRƯỜNG =====
    @Mapping(source = "lopId", target = "lop.id")
    @Mapping(source = "tenLop", target = "lop.ten")
    @Mapping(source = "truongId", target = "lop.truong.id")
    @Mapping(source = "tenTruong", target = "lop.truong.ten")

    // ❗ roles xử lý ở AfterMapping
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    User fromUserFullPro(UserFullPro dto);


    /* =======================
       AFTER MAPPING
       ======================= */

    @AfterMapping
    default void mapRoles(UserFullPro dto, @MappingTarget User user) {
        if (dto.getRoles() != null && !dto.getRoles().isBlank()) {
            List<String> roles = Arrays.stream(dto.getRoles().split(","))
                    .map(String::trim)
                    .toList();
            user.setRoles(roles);
        }
        if (dto.getPermissions() != null && !dto.getPermissions().isBlank()) {
            List<String> permissions = Arrays.stream(dto.getPermissions().split(","))
                    .map(String::trim)
                    .toList();
            user.setPermissions(permissions);
        }
    }
}
