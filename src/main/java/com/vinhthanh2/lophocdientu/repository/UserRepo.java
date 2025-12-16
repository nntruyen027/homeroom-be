package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.res.UserFullRes;
import com.vinhthanh2.lophocdientu.dto.sql.UserAuthPro;
import com.vinhthanh2.lophocdientu.dto.sql.UserFullPro;
import com.vinhthanh2.lophocdientu.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepo {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserMapper userMapper;

    @SuppressWarnings("unchecked")
    public Optional<UserFullRes> findByUsername(String username) {
        String sql = """
                SELECT * FROM auth.fn_lay_nguoi_dung_theo_username(:p_username);
                """;

        List<UserFullPro> list = entityManager.createNativeQuery(sql, UserFullPro.class)
                .setParameter("p_username", username)
                .getResultList();

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(userMapper.toUserFullRes(list.get(0)));
    }

    @SuppressWarnings("unchecked")
    public Optional<UserAuthPro> findAuthByUsername(String username) {
        String sql = """
                SELECT * FROM auth.fn_lay_thong_tin_auth_theo_username(:p_username);
                """;

        List<UserAuthPro> list = entityManager.createNativeQuery(sql, UserAuthPro.class)
                .setParameter("p_username", username)
                .getResultList();

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of((list.get(0)));
    }


    @SuppressWarnings("unchecked")
    public UserAuthPro findAuthById(Long id) {
        String sql = """
                SELECT * FROM auth.fn_lay_thong_tin_auth_theo_id(:p_id);
                """;

        List<UserAuthPro> list = entityManager.createNativeQuery(sql, UserAuthPro.class)
                .setParameter("p_id", id)
                .getResultList();

        return (list.get(0));
    }

    public boolean doiMatKhau(Long userId, String newPass) {
        String sql = """
                SELECT auth.fn_doi_mat_khau(:p_user_id, :p_new_pass);
                """;

        entityManager.createNativeQuery(sql);
        return true;
    }

    public UserFullRes findById(Long userId) {
        String sql = """
                SELECT * FROM auth.fn_lay_nguoi_dung_theo_id(:p_user_id);
                """;

        UserFullPro pro = (UserFullPro) entityManager.createNativeQuery(sql, UserFullPro.class)
                .setParameter("p_user_id", userId)
                .getSingleResult();

        return userMapper.toUserFullRes(pro);
    }

    public UserFullRes phanVaiTroChoNguoiDung(Long id, List<String> roles) {
        String sql = """
                SELECT * FROM auth.fn_phan_vai_tro_cho_nguoi_dung(:p_id, :p_roles);
                """;

        UserFullPro pro = (UserFullPro) entityManager.createNativeQuery(sql, UserFullPro.class)
                .setParameter("p_id", id)
                .setParameter("p_roles", roles.toArray(new String[0]))
                .getSingleResult();

        return userMapper.toUserFullRes(pro);
    }
}