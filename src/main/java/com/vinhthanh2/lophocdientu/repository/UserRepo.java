package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.sql.UserFullPro;
import com.vinhthanh2.lophocdientu.entity.User;
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
    public Optional<User> findByUsername(String username) {
        String sql = """
                SELECT * FROM auth.fn_lay_nguoi_dung_theo_username(:p_username);
                """;

        List<UserFullPro> list = entityManager.createNativeQuery(sql, UserFullPro.class)
                .setParameter("p_username", username)
                .getResultList();

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(userMapper.fromUserFullPro(list.get(0)));
    }

    public boolean doiMatKhau(Long userId, String newPass) {
        String sql = """
                SELECT auth.fn_doi_mat_khau(:p_user_id, :p_new_pass);
                """;

        entityManager.createNativeQuery(sql);
        return true;
    }

    public User findById(Long userId) {
        String sql = """
                SELECT * FROM auth.fn_lay_nguoi_dung_theo_id(:p_user_id);
                """;

        UserFullPro pro = (UserFullPro) entityManager.createNativeQuery(sql, UserFullPro.class)
                .setParameter("p_user_id", userId)
                .getSingleResult();

        return userMapper.fromUserFullPro(pro);
    }

    public User phanVaiTroChoNguoiDung(Long id, List<String> roles) {
        String sql = """
                SELECT * FROM auth.fn_phan_vai_tro_cho_nguoi_dung(:p_id, :p_roles);
                """;

        UserFullPro pro = (UserFullPro) entityManager.createNativeQuery(sql, UserFullPro.class)
                .setParameter("p_id", id)
                .setParameter("p_roles", roles.toArray(new String[0]))
                .getSingleResult();

        return userMapper.fromUserFullPro(pro);
    }
}