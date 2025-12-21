package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.res.HsOnlRes;
import com.vinhthanh2.lophocdientu.dto.sql.HsOnlPro;
import com.vinhthanh2.lophocdientu.mapper.PresenceMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class UserPresenceRepo {

    @PersistenceContext
    private EntityManager em;

    private final PresenceMapper mapper;

    // =========================
    // ONLINE / OFFLINE
    // =========================

    public void userOnline(Long userId, String sessionId) {
        em.createNativeQuery("""
                        select auth.fn_user_online(:userId, :sessionId)
                        """)
                .setParameter("userId", userId)
                .setParameter("sessionId", sessionId)
                .getSingleResult();
    }

    public void userOfflineBySession(String sessionId) {
        em.createNativeQuery("""
                        select auth.fn_user_offline_by_session(:sessionId)
                        """)
                .setParameter("sessionId", sessionId)
                .getSingleResult();
    }

    // =========================
    // AUTO OFFLINE (scheduler)
    // =========================

    public int autoOffline(int timeoutMinutes) {
        Object result = em.createNativeQuery("""
                        select auth.fn_auto_offline(:minutes)
                        """)
                .setParameter("minutes", timeoutMinutes)
                .getSingleResult();

        return ((Number) result).intValue();
    }

    // =========================
    // QUERY
    // =========================

    public boolean isUserOnline(Long userId) {
        Object result = em.createNativeQuery("""
                        select auth.fn_is_user_online(:userId)
                        """)
                .setParameter("userId", userId)
                .getSingleResult();

        return (Boolean) result;
    }

    public Long countHocSinhOnline(Long lopId) {
        Object result = em.createNativeQuery("""
                        select auth.fn_dem_hoc_sinh_online(:lopId)
                        """)
                .setParameter("lopId", lopId)
                .getSingleResult();

        return ((Number) result).longValue();
    }

    @SuppressWarnings("unchecked")
    public List<HsOnlRes> danhSachHocSinhOnline(Long lopId) {
        List<HsOnlPro> pros = em.createNativeQuery("""
                        select * from auth.fn_ds_hoc_sinh_online(:lopId)
                        """, HsOnlPro.class)
                .setParameter("lopId", lopId)
                .getResultList();

        return pros.stream().map(mapper::toRes).toList();
    }

    public Object getUserPresence(Long userId) {
        return em.createNativeQuery("""
                        select * from auth.fn_get_user_presence(:userId)
                        """)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public Optional<Long> findLopIdBySession(String sessionId) {
        Object result = em.createNativeQuery("""
                        select auth.fn_tim_lop_id_tu_session(:sessionId)
                        """)
                .setParameter("sessionId", sessionId)
                .getSingleResult();

        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(((Number) result).longValue());
    }
}
