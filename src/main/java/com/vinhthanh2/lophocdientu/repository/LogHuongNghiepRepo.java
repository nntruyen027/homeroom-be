package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.LogHdHuongNghiepReq;
import com.vinhthanh2.lophocdientu.dto.res.LogHdHuongNghiepRes;
import com.vinhthanh2.lophocdientu.dto.sql.LogHdHuongNghiepPro;
import com.vinhthanh2.lophocdientu.mapper.LogHdHuongNghiepMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class LogHuongNghiepRepo {
    @PersistenceContext
    private EntityManager entityManager;

    private final LogHdHuongNghiepMapper mapper;

    public List<LogHdHuongNghiepRes> layDsLog(Long userId, int page, int limit) {
        int offset = (page - 1) * limit;

        String sql = """
                select * from school.fn_lay_log_hd_huong_nghiep(
                              p_user_id := :user_id,
                              p_hd_id := :hd_id, 
                              p_offset := :offset, 
                              p_limit := :limit
                )
                """;

        @SuppressWarnings("unchecked")
        List<LogHdHuongNghiepPro> pros = entityManager.createNativeQuery(sql, LogHdHuongNghiepPro.class)
                .setParameter("user_id", userId)
                .setParameter("hd_id", null)
                .setParameter("limit", limit)
                .setParameter("offset", offset)
                .getResultList();

        return pros.stream().map(mapper::toDto).toList();
    }

    public LogHdHuongNghiepRes layLog(Long userId, Long hdId) {

        String sql = """
                select * from school.fn_lay_log_hd_huong_nghiep(
                              p_user_id := :user_id,
                              p_hd_id := :hd_id, 
                              p_offset := :offset, 
                              p_limit := :limit
                )
                """;

        LogHdHuongNghiepPro pros = (LogHdHuongNghiepPro) entityManager.createNativeQuery(sql, LogHdHuongNghiepPro.class)
                .setParameter("user_id", userId)
                .setParameter("hd_id", hdId)
                .setParameter("limit", 1)
                .setParameter("offset", 0)
                .getSingleResult();

        return mapper.toDto(pros);
    }

    public Long demDsLog(Long userId) {
        String sql = """
                    SELECT school.fn_dem_log_hd_huong_nghiep(:userId, :hdId)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("hdId", null)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number number) return number.longValue();

        return Long.parseLong(result.toString());
    }

    public LogHdHuongNghiepRes nhanXetLog(Long hdId, Long hsId, Long gvId, String nhanXet) {
        String sql = """
                select * from school.fn_nhan_xet_log_hd_huong_nghiep(
                              p_user_id := :hsId, 
                              p_hd_id := :hdId, 
                              p_nhan_xet := :nhanXet, 
                              p_gv_id := :gvId
                )
                """;

        LogHdHuongNghiepPro pro = (LogHdHuongNghiepPro) entityManager.createNativeQuery(sql, LogHdHuongNghiepPro.class)
                .setParameter("hdId", hdId)
                .setParameter("hsId", hsId)
                .setParameter("gvId", gvId)
                .setParameter("nhanXet", nhanXet)
                .getSingleResult();
        return mapper.toDto(pro);
    }

    public LogHdHuongNghiepRes taoLog(Long hdId, Long hsId, LogHdHuongNghiepReq logHdHuongNghiepReq) {
        String sql = """
                select * from school.fn_tao_log_hd_huong_nghiep(
                              p_user_id := :hsId, 
                              p_hd_id := :hdId, 
                              p_nn_quan_tam := :nnQuanTam, 
                              p_muc_do_hieu_biet := :mucDoHieuBiet, 
                              p_ky_nang_han_che := :kyNangHanChe, 
                              p_cai_thien := :caiThien
                )
                """;

        LogHdHuongNghiepPro pro = (LogHdHuongNghiepPro) entityManager.createNativeQuery(sql, LogHdHuongNghiepPro.class)
                .setParameter("hdId", hdId)
                .setParameter("hsId", hsId)
                .setParameter("nnQuanTam", logHdHuongNghiepReq.getNnQuanTam())
                .setParameter("mucDoHieuBiet", logHdHuongNghiepReq.getMucDoHieuBiet())
                .setParameter("kyNangHanChe", logHdHuongNghiepReq.getKyNangHanChe())
                .setParameter("caiThien", logHdHuongNghiepReq.getCaiThien())
                .getSingleResult();
        return mapper.toDto(pro);
    }
}
