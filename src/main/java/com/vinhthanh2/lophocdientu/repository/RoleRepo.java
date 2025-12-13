package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.RoleReq;
import com.vinhthanh2.lophocdientu.dto.sql.RolePro;
import com.vinhthanh2.lophocdientu.entity.Role;
import com.vinhthanh2.lophocdientu.mapper.RoleMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RoleRepo {
    @PersistenceContext
    private EntityManager entityManager;

    private final RoleMapper roleMapper;

    @SuppressWarnings("unchecked")
    public List<Role> layTatCaVaiTro(String search, int page, int size) {
        int offset = (page - 1) * size;

        String sql = """
                select * from auth.fn_lay_tat_ca_vai_tro(
                    :p_search,
                    :p_offset,
                    :p_limit
                )
                """;

        List<RolePro> pros = entityManager.createNativeQuery(sql, RolePro.class)
                .setParameter("p_search", search)
                .setParameter("p_offset", offset)
                .setParameter("p_limit", size)
                .getResultList();

        return pros.stream().map(roleMapper::fromPro).toList();
    }

    public Long demTatCaVaiTro(String search) {
        String sql = """
                    SELECT auth.fn_dem_tat_ca_vai_tro(:p_search)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_search", search)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }

    public Role taoVaiTro(RoleReq roleReq) {
        String sql = """
                SELECT * FROM auth.fn_tao_vai_tro(:p_name, :p_code);
                """;

        RolePro pro = (RolePro) entityManager.createNativeQuery(sql, RolePro.class)
                .setParameter("p_name", roleReq.getName())
                .setParameter("p_code", roleReq.getCode())
                .getSingleResult();

        return roleMapper.fromPro(pro);
    }

    public Role suaVaiTro(Long id, RoleReq roleReq) {
        String sql = """
                SELECT * FROM auth.fn_sua_vai_tro(:p_id, :p_name, :p_code);
                """;

        RolePro pro = (RolePro) entityManager.createNativeQuery(sql, RolePro.class)
                .setParameter("p_id", id)
                .setParameter("p_name", roleReq.getName())
                .setParameter("p_code", roleReq.getCode())
                .getSingleResult();

        return roleMapper.fromPro(pro);
    }

    public Role phanQuyenChoVaiTro(Long id, List<String> permissions) {
        String sql = """
                SELECT * FROM auth.fn_phan_quyen_cho_vai_tro(:p_id, :p_permissions);
                """;

        RolePro pro = (RolePro) entityManager.createNativeQuery(sql, RolePro.class)
                .setParameter("p_id", id)
                .setParameter("p_permissions", permissions.toArray(new String[0]))
                .getSingleResult();

        return roleMapper.fromPro(pro);

    }

    public void xoaVaiTro(Long id) {
        String sql = """
                SELECT auth.fn_xoa_vai_tro(:p_id)
                """;


        entityManager.createNativeQuery(sql).setParameter("p_id", id).getSingleResult();
    }

    public boolean coVaiTro(Long userId, String roleCode) {
        String sql = """
                    SELECT * FROM auth.fn_nguoi_dung_co_vai_tro(:p_user_id, :p_role_code);
                """;
        return !entityManager.createNativeQuery(sql)
                .setParameter("p_user_id", userId)
                .setParameter("p_role_code", roleCode)
                .getResultList()
                .isEmpty();
    }

    public boolean coVaiTroTheoMa(String code) {
        String sql = """
                SELECT auth.fn_co_ton_tai_vai_tro_theo_ma(:p_code);
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_code", code)
                .getSingleResult();

        // Postgres trả về Boolean
        return result != null && (Boolean) result;
    }

    public Role timVaiTroTheoMa(String code) {
        String sql = """
                SELECT * FROM  auth.fn_lay_vai_tro_theo_ma(:p_code);
                """;

        RolePro pro = (RolePro) entityManager.createNativeQuery(sql, RolePro.class)
                .setParameter("p_code", code)
                .getSingleResult();
        return roleMapper.fromPro(pro);
    }
}
