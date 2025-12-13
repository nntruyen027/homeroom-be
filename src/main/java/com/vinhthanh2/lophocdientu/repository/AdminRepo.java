package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.UpdateAdminReq;
import com.vinhthanh2.lophocdientu.dto.sql.AdminPro;
import com.vinhthanh2.lophocdientu.dto.sql.UserFullPro;
import com.vinhthanh2.lophocdientu.entity.User;
import com.vinhthanh2.lophocdientu.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AdminRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private final UserMapper userMapper;

    @SuppressWarnings("unchecked")
    public Optional<User> layQuanTriTheoUsername(String username) {
        String sql = """
                SELECT * FROM auth.fn_lay_admin_theo_username(:p_username)
                """;

        List<AdminPro> pros = entityManager.createNativeQuery(sql, AdminPro.class)
                .setParameter("p_username", username)
                .getResultList();

        if (pros.isEmpty()) return Optional.empty();

        return Optional.of(userMapper.fromAdminPro(pros.get(0)));
    }

    public User taoNguoiDungQuanTri(String username, String hoTen, String password, String avatar) {
        String sql = """
                SELECT * FROM auth.fn_tao_nguoi_dung_quan_tri(
                              :p_username,
                              :p_ho_ten,
                              :p_password,
                              :p_avatar
                )
                """;
        UserFullPro pro = (UserFullPro) entityManager.createNativeQuery(sql, UserFullPro.class)
                .setParameter("p_username", username)
                .setParameter("p_ho_ten", hoTen)
                .setParameter("p_password", password)
                .setParameter("p_avatar", avatar)
                .getSingleResult();
        return userMapper.fromUserFullPro(pro);

    }

    // ============================================================
    // SỬA HỌC SINH
    // ============================================================
    @Transactional
    public User suaCaNhanAdmin(Long id, UpdateAdminReq req) {

        String sql = """
                    SELECT * FROM auth.fn_cap_nhat_thong_tin_quan_tri_vien(
                        :p_id,
                        :p_avatar,
                        :p_ho_ten
                    )
                """;

        AdminPro pro = (AdminPro) entityManager.createNativeQuery(sql, AdminPro.class)
                .setParameter("p_id", id)
                .setParameter("p_avatar", req.getAvatar())
                .setParameter("p_ho_ten", req.getHoTen())
                .getSingleResult();

        return userMapper.fromAdminPro(pro);
    }

}
