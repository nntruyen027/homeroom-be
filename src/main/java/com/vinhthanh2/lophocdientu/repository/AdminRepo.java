package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.AdminRequest;
import com.vinhthanh2.lophocdientu.dto.req.UpdateAdminReq;
import com.vinhthanh2.lophocdientu.dto.res.UserFullRes;
import com.vinhthanh2.lophocdientu.dto.sql.UserFullPro;
import com.vinhthanh2.lophocdientu.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AdminRepo {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public UserFullRes taoQuanTriVien(AdminRequest adminRequest) {
        String sql = """
                select  * from auth.fn_tao_nguoi_dung_quan_tri(
                 :p_username, 
                 :p_ho_ten,
                :p_password,
                
                               :p_avatar
                );
                
                """;

        UserFullPro pro = (UserFullPro) entityManager.createNativeQuery(sql, UserFullPro.class)
                .setParameter("p_username", adminRequest.getUserName())
                .setParameter("p_ho_ten", adminRequest.getHoTen())
                .setParameter("p_password", passwordEncoder.encode(adminRequest.getPassword()))
                .setParameter("p_avatar", adminRequest.getAvatar())
                .getSingleResult();

        return adminMapper.toUserFullRes(pro);
    }

    public UserFullRes capNhatQuanTriVien(Long id, UpdateAdminReq adminRequest) {
        String sql = """
                        select  * from auth.fn_cap_nhat_thong_tin_quan_tri_vien(
                        :id,
                        :p_avatar,
                        :p_ho_ten
                
                        );
                
                """;

        UserFullPro pro = (UserFullPro) entityManager.createNativeQuery(sql, UserFullPro.class)
                .setParameter("id", id)
                .setParameter("p_ho_ten", adminRequest.getHoTen())
                .setParameter("p_avatar", adminRequest.getAvatar())
                .getSingleResult();

        return adminMapper.toUserFullRes(pro);
    }
}
