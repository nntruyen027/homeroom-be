package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.TeacherRegisterReq;
import com.vinhthanh2.lophocdientu.dto.req.UpdateTeacherReq;
import com.vinhthanh2.lophocdientu.dto.res.TeacherRes;
import com.vinhthanh2.lophocdientu.dto.sql.GiaoVienPro;
import com.vinhthanh2.lophocdientu.mapper.GiaoVienMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class GiaoVienRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private final GiaoVienMapper giaoVienMapper;

    @SuppressWarnings("unchecked")
    public List<TeacherRes> layTatCaGiaoVien(String search, int page, int size) {
        int offset = (page - 1) * size;

        String sql = """
                SELECT * FROM auth.fn_lay_tat_ca_giao_vien(
                    :p_search, :p_offset, :p_limit
                )
                """;

        List<GiaoVienPro> gvPro = entityManager.createNativeQuery(sql, GiaoVienPro.class)
                .setParameter("p_search", search)
                .setParameter("p_offset", offset)
                .setParameter("p_limit", size)
                .getResultList().stream().toList();

        return gvPro.stream().map(giaoVienMapper::toGiaoVienRes).toList();
    }


    public Long demTatCaGiaoVien(String search) {
        String sql = """
                    SELECT auth.fn_dem_tat_ca_giao_vien(:p_search)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_search", search)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }

    // ============================================================
    // TẠO GIÁO VIÊN
    // ============================================================
    @Transactional
    public TeacherRes taoGiaoVien(TeacherRegisterReq req) {

        String sql = """
                    SELECT * FROM auth.fn_tao_giao_vien(
                        :p_username,
                        :p_password,
                        :p_ho_ten,
                        :p_bo_mon,
                        :p_chuc_vu,
                        :p_ngay_sinh,
                        :p_la_nam,
                        :p_xa_id,
                        :p_dia_chi_chi_tiet
                    )
                """;


        return giaoVienMapper.toGiaoVienRes((GiaoVienPro) entityManager.createNativeQuery(sql, GiaoVienPro.class)
                .setParameter("p_username", req.getUsername())
                .setParameter("p_password", req.getPassword())
                .setParameter("p_ho_ten", req.getHoTen())
                .setParameter("p_la_nam", req.getLaNam())
                .setParameter("p_bo_mon", req.getBoMon())
                .setParameter("p_chuc_vu", req.getChucVu())
                .setParameter("p_xa_id", req.getXaId())
                .setParameter("p_ngay_sinh", req.getNgaySinh())
                .setParameter("p_dia_chi_chi_tiet", req.getDiaChiChiTiet())
                .getSingleResult());
    }

    // ============================================================
    // SỬA GIÁO VIÊN
    // ============================================================
    @Transactional
    public TeacherRes suaGiaoVien(Long id, UpdateTeacherReq req) {

        String sql = """
                    SELECT * FROM auth.fn_cap_nhat_giao_vien(
                        :p_id,
                        :p_bo_mon,
                        :p_chuc_vu
                    )
                """;

        GiaoVienPro giaoVienPro = (GiaoVienPro) entityManager.createNativeQuery(sql, GiaoVienPro.class)
                .setParameter("p_id", id)
                .setParameter("p_bo_mon", req.getBoMon())
                .setParameter("p_chuc_vu", req.getChucVu())
                .getSingleResult();

        return giaoVienMapper.toGiaoVienRes(giaoVienPro);
    }

    @Transactional
    public TeacherRes suaGiaoVienFull(Long id, UpdateTeacherReq req) {

        String sql = """
                    SELECT * FROM auth.fn_cap_nhat_giao_vien_full(
                        p_user_id := :userId, 
                        p_ho_ten := :hoTen, 
                        p_avatar := :avatar, 
                        p_ngay_sinh := :ngaySinh, 
                        p_xa_id := :xaId, 
                        p_dia_chi_chi_tiet := :diaChiChiTiet, 
                        p_la_nam := :laNam, 
                        p_bo_mon := :boMon, 
                        p_chuc_vu := :chucVu
                    )
                """;

        GiaoVienPro giaoVienPro = (GiaoVienPro) entityManager.createNativeQuery(sql, GiaoVienPro.class)
                .setParameter("userId", id)
                .setParameter("hoTen", req.getHoTen())
                .setParameter("avatar", req.getAvatar())
                .setParameter("ngaySinh", req.getNgaySinh())
                .setParameter("xaId", req.getXaId())
                .setParameter("diaChiChiTiet", req.getDiaChiChiTiet())
                .setParameter("laNam", req.getLaNam())
                .setParameter("boMon", req.getBoMon())
                .setParameter("chucVu", req.getChucVu())
                .getSingleResult();

        return giaoVienMapper.toGiaoVienRes(giaoVienPro);
    }

    // ============================================================
    // XÓA GIÁO VIÊN
    // ============================================================
    @Transactional
    public boolean xoaGiaoVien(Long id) {
        String sql = """
                    SELECT auth.fn_xoa_nguoi_dung(:p_id)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_id", id)
                .getSingleResult();

        if (result instanceof Boolean b) return b;
        return Boolean.parseBoolean(result.toString());
    }

    public TeacherRes layGiaoVienTheoId(Long giaoVienId) {
        String sql = """
                select * from auth.fn_lay_giao_vien_theo_id(:id);
                """;

        GiaoVienPro teacherRes = (GiaoVienPro) entityManager.createNativeQuery(sql, GiaoVienPro.class)
                .setParameter("id", giaoVienId)
                .getSingleResult();

        return giaoVienMapper.toGiaoVienRes(teacherRes);
    }


}
