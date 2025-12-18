package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.ThongBaoReq;
import com.vinhthanh2.lophocdientu.dto.res.ThongBaoViewRes;
import com.vinhthanh2.lophocdientu.dto.sql.ThongBaoViewPro;
import com.vinhthanh2.lophocdientu.mapper.ThongBaoMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ThongBaoRepo {
    @PersistenceContext
    private EntityManager em;

    private final ThongBaoMapper mapper;

    public List<ThongBaoViewRes> layTatCaThongBao(Long lopId, Long nguoiTaoId, String search, int page, int limit) {
        int offset = (page - 1) * limit;

        String sql = """
                select * from message.fn_lay_thong_bao(
                              p_lop_id := :lopId, 
                              p_nguoi_tao_id := :nguoiTaoId, 
                              p_search := :search, 
                              p_offset := :offset, 
                              p_limit := :limit
                )
                """;

        @SuppressWarnings("unchecked")
        List<ThongBaoViewPro> pros = em.createNativeQuery(sql, ThongBaoViewPro.class)
                .setParameter("lopId", lopId)
                .setParameter("nguoiTaoId", nguoiTaoId)
                .setParameter("search", search)
                .setParameter("offset", offset)
                .setParameter("limit", limit)
                .getResultList();


        return pros.stream().map(mapper::toRes).toList();
    }

    public Long demTatCaThongBap(Long lopId, Long nguoiTaoId, String search) {
        String sql = """
                    SELECT message.fn_dem_thong_bao(
                           p_lop_id := :lopId, p_nguoi_tao_id := :nguoiTaoId, p_search := :search
                    )
                """;

        Object result = em.createNativeQuery(sql)
                .setParameter("search", search)
                .setParameter("lopId", lopId)
                .setParameter("nguoiTaoId", nguoiTaoId)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }

    public ThongBaoViewRes taoThongBao(Long lopId, Long nguoiTaoId, ThongBaoReq thongBaoReq) {
        String sql = """
                select * from message.fn_them_thong_bao(
                              p_tieu_de := :tieuDe, 
                              p_noi_dung := :noiDung, 
                              p_lop_id := :lopId, 
                              p_nguoi_tao_id := :nguoiTaoId
                )
                """;

        ThongBaoViewPro pro = (ThongBaoViewPro) em.createNativeQuery(sql, ThongBaoViewPro.class)
                .setParameter("lopId", lopId)
                .setParameter("nguoiTaoId", nguoiTaoId)
                .setParameter("tieuDe", thongBaoReq.getTieuDe())
                .setParameter("noiDung", thongBaoReq.getNoiDung())
                .getSingleResult();

        return mapper.toRes(pro);
    }

    public ThongBaoViewRes suaThongBao(Long id, ThongBaoReq thongBaoReq) {
        String sql = """
                select * from message.fn_cap_nhat_thong_bao(
                              p_id := :id, 
                              p_tieu_de := :tieuDe, 
                              p_noi_dung := :noiDung
                )
                """;

        ThongBaoViewPro pro = (ThongBaoViewPro) em.createNativeQuery(sql, ThongBaoViewPro.class)
                .setParameter("id", id)
                .setParameter("tieuDe", thongBaoReq.getTieuDe())
                .setParameter("noiDung", thongBaoReq.getNoiDung())
                .getSingleResult();

        return mapper.toRes(pro);
    }

    public void xoaThongBao(Long id) {
        em.createNativeQuery("select message.fn_xoa_thong_bao(:id)")
                .setParameter("id", id)
                .getSingleResult();
    }

    public void xemThongBao(Long thongBaoId, Long hsId) {
        em.createNativeQuery("select message.fn_danh_dau_da_xem_thong_bao(p_thong_bao_id := :thongBaoId, p_user_id := :hsId)")
                .setParameter("thongBaoId", thongBaoId)
                .setParameter("hsId", hsId)
                .getSingleResult();
    }
}
