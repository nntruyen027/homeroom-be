package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.StudentRegisterReq;
import com.vinhthanh2.lophocdientu.dto.req.UpdateStudentReq;
import com.vinhthanh2.lophocdientu.dto.sql.HocSinhPro;
import com.vinhthanh2.lophocdientu.entity.User;
import com.vinhthanh2.lophocdientu.exception.AppException;
import com.vinhthanh2.lophocdientu.mapper.HocSinhMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class HocSinhRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private final HocSinhMapper hocSinhMapper;

    // ============================================================
    // LẤY HỌC SINH THEO LỚP
    // ============================================================
    @SuppressWarnings("unchecked")
    public List<User> layHocSinhTheoLop(Long lopId, String search, int page, int size) {
        int offset = (page - 1) * size;

        String sql = """
                    SELECT * FROM school.fn_lay_hoc_sinh_theo_lop(
                        :p_lop_id, :p_search, :p_offset, :p_limit
                    )
                """;

        List<HocSinhPro> pros = entityManager.createNativeQuery(sql, HocSinhPro.class)
                .setParameter("p_lop_id", lopId)
                .setParameter("p_search", search)
                .setParameter("p_offset", offset)
                .setParameter("p_limit", size)
                .getResultList().stream().toList();

        return pros.stream().map(hocSinhMapper::fromHocSinhPro).toList();
    }

    // ============================================================
    // ĐẾM HỌC SINH THEO LỚP
    // ============================================================
    public Long demHocSinhTheoLop(Long lopId, String search) {
        String sql = """
                    SELECT school.fn_dem_hoc_sinh_theo_lop(:p_lop_id, :p_search)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_lop_id", lopId)
                .setParameter("p_search", search)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number number) return number.longValue();

        return Long.parseLong(result.toString());
    }

    // ============================================================
    // LẤY HỌC SINH THEO ID
    // ============================================================
    public User layHocSinhTheoId(Long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new AppException("Không tìm thấy học sinh có ID: " + id, "USER_NOT_FOUND");
        }
        return user;
    }

    // ============================================================
    // TẠO HỌC SINH (gọi function SQL)
    // ============================================================
    @Transactional
    public User taoHocSinh(StudentRegisterReq req) {

        String sql = """
                    SELECT * FROM school.fn_tao_hoc_sinh(
                        :p_username,
                        :p_password,
                        :p_avatar,
                        :p_role,
                        :p_ho_ten,
                        :p_lop_id,
                        :p_ngay_sinh,
                        :p_la_nam,
                        :p_so_thich,
                        :p_mon_hoc_yeu_thich,
                        :p_diem_manh,
                        :p_diem_yeu,
                        :p_nghe_nghiep_mong_muon,
                        :p_nhan_xet_giao_vien,
                        :p_ghi_chu,
                        :p_realistic_score,
                        :p_investigative_score,
                        :p_artistic_score,
                        :p_social_score,
                        :p_enterprising_score,
                        :p_conventional_score,
                        :p_assessment_result,
                        :p_ten_cha,
                        :p_ns_cha,
                        :p_sdt_cha,
                        :p_ten_me,
                        :p_ns_me,
                        :p_sdt_me,
                        :p_ten_ph_khac,
                        :p_ns_ph_khac,
                        :p_sdt_ph_khac,
                        :p_xa_id
                    )
                """;

        return hocSinhMapper.fromHocSinhPro((HocSinhPro) entityManager.createNativeQuery(sql, HocSinhPro.class)
                .setParameter("p_username", req.getUsername())
                .setParameter("p_password", req.getPassword())
                .setParameter("p_avatar", req.getAvatar())
                .setParameter("p_role", req.getRole())
                .setParameter("p_ho_ten", req.getHoTen())
                .setParameter("p_lop_id", req.getLop().getId())
                .setParameter("p_ngay_sinh", req.getNgaySinh())
                .setParameter("p_la_nam", req.getLaNam())
                .setParameter("p_so_thich", req.getSoThich())
                .setParameter("p_mon_hoc_yeu_thich", req.getMonHocYeuThich())
                .setParameter("p_diem_manh", req.getDiemManh())
                .setParameter("p_diem_yeu", req.getDiemYeu())
                .setParameter("p_nghe_nghiep_mong_muon", req.getNgheNghiepMongMuon())
                .setParameter("p_nhan_xet_giao_vien", req.getNhanXetGiaoVien())
                .setParameter("p_ghi_chu", req.getGhiChu())

                .setParameter("p_realistic_score", req.getRealisticScore())
                .setParameter("p_investigative_score", req.getInvestigativeScore())
                .setParameter("p_artistic_score", req.getArtisticScore())
                .setParameter("p_social_score", req.getSocialScore())
                .setParameter("p_enterprising_score", req.getEnterprisingScore())
                .setParameter("p_conventional_score", req.getConventionalScore())
                .setParameter("p_assessment_result", req.getAssessmentResult())

                .setParameter("p_ten_cha", req.getTenCha())
                .setParameter("p_ns_cha", req.getNsCha())
                .setParameter("p_sdt_cha", req.getSdtCha())
                .setParameter("p_ten_me", req.getTenMe())
                .setParameter("p_ns_me", req.getNsMe())
                .setParameter("p_sdt_me", req.getSdtMe())
                .setParameter("p_ten_ph_khac", req.getTenPhKhac())
                .setParameter("p_ns_ph_khac", req.getNsPhKhac())
                .setParameter("p_sdt_ph_khac", req.getSdtPhKhac())
                .setParameter("p_xa_id", req.getXaId())

                .getSingleResult());
    }

    // ============================================================
    // SỬA HỌC SINH
    // ============================================================
    @Transactional
    public User suaHocSinh(UpdateStudentReq req) {

        String sql = """
                    SELECT * FROM school.fn_sua_hoc_sinh(
                        :p_username,
                        :p_avatar,
                        :p_ho_ten,
                        :p_lop_id,
                        :p_ngay_sinh,
                        :p_la_nam,
                        :p_so_thich,
                        :p_mon_hoc_yeu_thich,
                        :p_diem_manh,
                        :p_diem_yeu,
                        :p_nghe_nghiep_mong_muon,
                        :p_nhan_xet_giao_vien,
                        :p_ghi_chu,
                        :p_realistic_score,
                        :p_investigative_score,
                        :p_artistic_score,
                        :p_social_score,
                        :p_enterprising_score,
                        :p_conventional_score,
                        :p_assessment_result,
                        :p_ten_cha,
                        :p_ns_cha,
                        :p_sdt_cha,
                        :p_ten_me,
                        :p_ns_me,
                        :p_sdt_me,
                        :p_ten_ph_khac,
                        :p_ns_ph_khac,
                        :p_sdt_ph_khac,
                        :p_xa_id
                    )
                """;

        return hocSinhMapper.fromHocSinhPro((HocSinhPro) entityManager.createNativeQuery(sql, HocSinhPro.class)
                .setParameter("p_username", req.getUsername())
                .setParameter("p_avatar", req.getAvatar())
                .setParameter("p_ho_ten", req.getHoTen())
                .setParameter("p_lop_id", req.getLopId())

                .setParameter("p_ngay_sinh", req.getNgaySinh())
                .setParameter("p_la_nam", req.getLaNam())
                .setParameter("p_so_thich", req.getSoThich())
                .setParameter("p_mon_hoc_yeu_thich", req.getMonHocYeuThich())
                .setParameter("p_diem_manh", req.getDiemManh())
                .setParameter("p_diem_yeu", req.getDiemYeu())
                .setParameter("p_nghe_nghiep_mong_muon", req.getNgheNghiepMongMuon())
                .setParameter("p_nhan_xet_giao_vien", req.getNhanXetGiaoVien())
                .setParameter("p_ghi_chu", req.getGhiChu())

                .setParameter("p_realistic_score", req.getRealisticScore())
                .setParameter("p_investigative_score", req.getInvestigativeScore())
                .setParameter("p_artistic_score", req.getArtisticScore())
                .setParameter("p_social_score", req.getSocialScore())
                .setParameter("p_enterprising_score", req.getEnterprisingScore())
                .setParameter("p_conventional_score", req.getConventionalScore())
                .setParameter("p_assessment_result", req.getAssessmentResult())

                .setParameter("p_ten_cha", req.getTenCha())
                .setParameter("p_ns_cha", req.getNsCha())
                .setParameter("p_sdt_cha", req.getSdtCha())
                .setParameter("p_ten_me", req.getTenMe())
                .setParameter("p_ns_me", req.getNsMe())
                .setParameter("p_sdt_me", req.getSdtMe())
                .setParameter("p_ten_ph_khac", req.getTenPhKhac())
                .setParameter("p_ns_ph_khac", req.getNsPhKhac())
                .setParameter("p_sdt_ph_khac", req.getSdtPhKhac())
                .setParameter("p_xa_id", req.getXaId())

                .getSingleResult());
    }

    // ============================================================
    // XÓA HỌC SINH
    // ============================================================
    @Transactional
    public boolean xoaHocSinh(Long id) {

        String sql = """
                    SELECT school.fn_xoa_hoc_sinh(:p_id)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_id", id)
                .getSingleResult();

        if (result instanceof Boolean b) return b;
        return Boolean.parseBoolean(result.toString());
    }
}
