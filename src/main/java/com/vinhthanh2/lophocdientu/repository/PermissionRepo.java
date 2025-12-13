package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.sql.PermissionPro;
import com.vinhthanh2.lophocdientu.entity.Permission;
import com.vinhthanh2.lophocdientu.mapper.PermissionMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class PermissionRepo {
    @PersistenceContext
    private EntityManager entityManager;
    private final PermissionMapper permissionMapper;

    @SuppressWarnings("unchecked")
    public Set<String> timPermissionTheoUserId(Long userId) {
        String sql = """
                SELECT permission_code FROM auth.fn_lay_permission_theo_user(:p_user_id);
                """;

        List<String> perms = entityManager.createNativeQuery(sql)
                .setParameter("p_user_id", userId)
                .getResultList();

        return perms.stream().collect(Collectors.toSet());
    }

    @Transactional
    public Permission taoPermission(String code) {
        String sql = """
                SELECT * FROM auth.fn_tao_permission(:p_code);
                """;

        PermissionPro pro = (PermissionPro) entityManager.createNativeQuery(sql, PermissionPro.class)
                .setParameter("p_code", code)
                .getSingleResult();

        return permissionMapper.fromPro(pro);
    }

    public Boolean coPermission(String code) {
        String sql = """
                SELECT auth.fn_kiem_tra_ton_tai_permission_theo_code(:p_code);
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_code", code)
                .getSingleResult();

        return result != null && (Boolean) result;
    }

    @SuppressWarnings("unchecked")
    public List<Permission> layTatCaQuyen(String search, int page, int size) {
        int offset = (page - 1) * size;

        String sql = """
                select * from auth.fn_lay_tat_ca_quyen(
                    :p_search,
                    :p_offset,
                    :p_limit
                )
                """;

        List<PermissionPro> pros = entityManager.createNativeQuery(sql, PermissionPro.class)
                .setParameter("p_search", search)
                .setParameter("p_offset", offset)
                .setParameter("p_limit", size)
                .getResultList();

        return pros.stream().map(permissionMapper::fromPro).toList();
    }

    public Long demTatCaQuyen(String search) {
        String sql = """
                    SELECT auth.fn_dem_tat_ca_quyen(:p_search)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_search", search)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }

}
