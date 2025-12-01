package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.LopReq;
import com.vinhthanh2.lophocdientu.dto.sql.LopPro;
import com.vinhthanh2.lophocdientu.entity.Lop;
import com.vinhthanh2.lophocdientu.mapper.LopMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class LopRepo {
    @PersistenceContext
    private EntityManager entityManager;
    final private LopMapper lopMapper;

    @SuppressWarnings("unchecked")
    public List<Lop> layLopTheoTruong(Long schoolId, String search, int page, int size) {
        int offset = (page - 1) * size;

        String sql = """
                select * from school.fn_lay_tat_ca_lop_thuoc_truong(
                    :p_truong_id,
                    :p_search,
                    :p_offset,
                    :p_limit
                )
                """;

        List<LopPro> lopPros = entityManager.createNativeQuery(sql, LopPro.class)
                .setParameter("p_truong_id", schoolId)
                .setParameter("p_search", search)
                .setParameter("p_offset", offset)
                .setParameter("p_limit", size)
                .getResultList();

        return lopPros.stream().map(lopMapper::fromPro).toList();
    }

    public Long demTatCaLopThuocTruong(Long truongId, String search) {
        String sql = """
                    select school.fn_dem_tat_ca_lop_thuoc_truong(
                        :p_truong_id,
                        :p_search
                    )
                """;
        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_truong_id", truongId)
                .setParameter("p_search", search)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }

    @SuppressWarnings("unchecked")
    public List<Lop> layTatCaLopThuocGiaoVien(Long giaoVienId, String search, int page, int size) {
        int offset = (page - 1) * size;
        String sql = """
                    select * from school.fn_lay_tat_ca_lop_thuoc_giao_vien(
                        :p_giao_vien_id,
                        :p_search,
                        :p_offset,
                        :p_size
                    )
                """;
        List<LopPro> lopPros = entityManager.createNativeQuery(sql, Lop.class)
                .setParameter("p_giao_vien_id", giaoVienId)
                .setParameter("p_search", search)
                .setParameter("p_offset", offset)
                .setParameter("p_size", size)
                .getResultList();

        return lopPros.stream().map(lopMapper::fromPro).toList();
    }

    public Long demTatCaLopThuocGiaoVien(Long giaoVienId, String search) {
        String sql = """
                    select school.fn_dem_tat_ca_lop_thuoc_giao_vien(
                        :p_giao_vien_id,
                        :p_search
                    )
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_giao_vien_id", giaoVienId)
                .setParameter("p_search", search)
                .getSingleResult();

        if (result == null) return 0L;

        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }

    @Transactional
    public Lop taoLop(LopReq lopReq) {
        String sql = """
                    select school.fn_tao_lop(
                        p_ten,
                        p_hinh_anh,
                        p_truong_id,
                        p_giao_vien_id
                    )
                """;

        LopPro lopPro = (LopPro) entityManager.createNativeQuery(sql, LopPro.class)
                .setParameter("p_ten", lopReq.getTen())
                .setParameter("p_hinh_anh", lopReq.getHinhAnh())
                .setParameter("p_truong_id", lopReq.getTruongId())
                .setParameter("p_giao_vien_id", lopReq.getGiaoVienId())
                .getSingleResult();

        return lopMapper.fromPro(lopPro);
    }

    @Transactional
    public Lop suaLop(Long id, LopReq lopReq, Long nguoiSuaId) {
        String sql = """
                    select school.fn_sua_lop(
                        p_id,
                        p_ten,
                        p_hinh_anh,
                        p_truong_id,
                        p_giao_vien_id,
                        p_nguoi_sua_id
                    )
                """;

        LopPro lopPro = (LopPro) entityManager.createNativeQuery(sql, LopPro.class)
                .setParameter("p_id", id)
                .setParameter("p_ten", lopReq.getTen())
                .setParameter("p_hinh_anh", lopReq.getHinhAnh())
                .setParameter("p_truong_id", lopReq.getTruongId())
                .setParameter("p_giao_vien_id", lopReq.getGiaoVienId())
                .setParameter("p_nguoi_sua_id", nguoiSuaId)
                .getSingleResult();

        return lopMapper.fromPro(lopPro);
    }

    @Transactional
    public boolean xoaLop(Long lopId, Long nguoiXoaId) {
        String sql = """
                    SELECT school.fn_xoa_lop(:p_lop_id, :p_nguoi_xoa_id)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_lop_id", lopId)
                .setParameter("p_nguoi_xoa_id", nguoiXoaId)
                .getSingleResult();

        if (result instanceof Boolean b) return b;
        return Boolean.parseBoolean(result.toString());
    }
}
