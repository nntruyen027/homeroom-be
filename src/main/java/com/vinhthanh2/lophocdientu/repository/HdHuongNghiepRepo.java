package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.HdHuongNghiepReq;
import com.vinhthanh2.lophocdientu.dto.res.HdHuongNghiepRes;
import com.vinhthanh2.lophocdientu.dto.sql.HdHuongNghiepPro;
import com.vinhthanh2.lophocdientu.mapper.HdHuongNghiepMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class HdHuongNghiepRepo {
    @PersistenceContext
    private EntityManager entityManager;

    private final HdHuongNghiepMapper hdHuongNghiepMapper;

    @SuppressWarnings("unchecked")
    public List<HdHuongNghiepRes> layTatCaHoatDong(Long nguoiTaoId, String search, int page, int limit) {
        int offset = (page - 1) * limit;

        String sql = """
                select * from school.fn_lay_tat_ca_hoat_dong_huong_nghiep(
                              :id_nguoi_tao,
                              :p_search,
                              :p_page,
                              :p_limit
                )
                """;

        List<HdHuongNghiepPro> pros = entityManager.createNativeQuery(sql, HdHuongNghiepPro.class)
                .setParameter("id_nguoi_tao", nguoiTaoId)
                .setParameter("p_search", search)
                .setParameter("p_page", offset)
                .setParameter("p_limit", limit)
                .getResultList();
        ;
        return pros.stream().map(hdHuongNghiepMapper::toDto).toList();
    }

    public Long demTatCaHoatDong(Long userId, String search) {
        String sql = """
                    SELECT school.fn_dem_tat_ca_hoat_dong_huong_nghiep(:user_id, :p_search)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("user_id", userId)
                .setParameter("p_search", search)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }

    @Transactional
    public HdHuongNghiepRes taoHoatDong(HdHuongNghiepReq hdHuongNghiepReq, Long nguoiTaoId) {
        String sql = """
                select * from school.fn_tao_hoat_dong_huong_nghiep(
                    :p_ten,
                    :p_thoi_gian_bat_dau,
                    :p_thoi_gian_ket_thuc,
                    :p_ghi_chu,
                    :p_nguoi_tao_id
                )
                """;

        HdHuongNghiepPro pro = (HdHuongNghiepPro) entityManager.createNativeQuery(sql, HdHuongNghiepPro.class)
                .setParameter("p_ten", hdHuongNghiepReq.getTenHoatDong())
                .setParameter("p_thoi_gian_bat_dau", hdHuongNghiepReq.getThoiGianBatDau())
                .setParameter("p_thoi_gian_ket_thuc", hdHuongNghiepReq.getThoiGianKetThuc())
                .setParameter("p_ghi_chu", hdHuongNghiepReq.getGhiChu())
                .setParameter("p_nguoi_tao_id", nguoiTaoId)
                .getSingleResult();

        return hdHuongNghiepMapper.toDto(pro);

    }

    @Transactional
    public HdHuongNghiepRes suaHoatDong(Long hdId, HdHuongNghiepReq hdHuongNghiepReq) {
        String sql = """
                select * from school.fn_sua_hoat_dong_huong_nghiep(
                    :p_id,
                    :p_ten,
                    :p_thoi_gian_bat_dau,
                    :p_thoi_gian_ket_thuc,
                    :p_ghi_chu
                )
                """;

        HdHuongNghiepPro pro = (HdHuongNghiepPro) entityManager.createNativeQuery(sql, HdHuongNghiepPro.class)
                .setParameter("p_ten", hdHuongNghiepReq.getTenHoatDong())
                .setParameter("p_thoi_gian_bat_dau", hdHuongNghiepReq.getThoiGianBatDau())
                .setParameter("p_thoi_gian_ket_thuc", hdHuongNghiepReq.getThoiGianKetThuc())
                .setParameter("p_ghi_chu", hdHuongNghiepReq.getGhiChu())
                .setParameter("p_id", hdId)
                .getSingleResult();


        return hdHuongNghiepMapper.toDto(pro);

    }

    @Transactional
    public void xoaHoatDong(Long id) {
        String sql = "select school.fn_xoa_hoat_dong_huong_nghiep(:p_id)";

        entityManager.createNativeQuery(sql)
                .setParameter("p_id", id)
                .getSingleResult();
    }

    public HdHuongNghiepRes phanHoatDongChoLop(Long id, List<Long> lopIds) {

        String lopIdSql =
                lopIds.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));

        String sql = """
                select * from school.fn_phan_hoat_dong_cho_lop(
                    %d,
                    ARRAY[%s]::bigint[]
                )
                """.formatted(id, lopIdSql);

        HdHuongNghiepPro pros =
                (HdHuongNghiepPro) entityManager
                        .createNativeQuery(sql, HdHuongNghiepPro.class)
                        .getSingleResult();

        return hdHuongNghiepMapper.toDto(pros);
    }

    @SuppressWarnings("unchecked")
    public List<HdHuongNghiepRes> layTatCaHoatDongTheoHs(Long userId, String search, int page, int limit) {
        int offset = (page - 1) * limit;

        System.out.println(offset);

        String sql = """
                select * from school.fn_lay_hdhn_theo_hoc_sinh(
                              :id_nguoi_tao,
                              :p_search,
                              :p_offset,
                              :p_limit
                )
                """;

        List<HdHuongNghiepPro> pros = entityManager.createNativeQuery(sql, HdHuongNghiepPro.class)
                .setParameter("id_nguoi_tao", userId)
                .setParameter("p_search", search)
                .setParameter("p_offset", offset)
                .setParameter("p_limit", limit)
                .getResultList();
        ;
        return pros.stream().map(hdHuongNghiepMapper::toDto).toList();
    }

    public Long demTatCaHoatDongTheoHs(Long userId, String search) {
        String sql = """
                    SELECT school.fn_dem_hdhn_theo_hoc_sinh(:user_id, :p_search)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("user_id", userId)
                .setParameter("p_search", search)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }
}
