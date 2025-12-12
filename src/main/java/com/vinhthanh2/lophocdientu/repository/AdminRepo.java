package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.UpdateAdminReq;
import com.vinhthanh2.lophocdientu.dto.sql.AdminPro;
import com.vinhthanh2.lophocdientu.entity.User;
import com.vinhthanh2.lophocdientu.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AdminRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private final UserMapper userMapper;

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
