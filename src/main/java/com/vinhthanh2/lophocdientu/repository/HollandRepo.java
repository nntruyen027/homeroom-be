package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.KetQuaHollandReq;
import com.vinhthanh2.lophocdientu.dto.res.KetQuaHollandRes;
import com.vinhthanh2.lophocdientu.dto.res.ThongKeHollandTheoLopRes;
import com.vinhthanh2.lophocdientu.dto.sql.KetQuaHollandPro;
import com.vinhthanh2.lophocdientu.dto.sql.ThongKeHollandTheoLopPro;
import com.vinhthanh2.lophocdientu.mapper.HollandMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HollandRepo {
    @PersistenceContext
    private EntityManager em;

    private final HollandMapper mapper;

    public List<KetQuaHollandRes> layDsHollandTheoLop(Long lopId) {
        String sql = """
                select * from school.lay_ds_kq_holland_theo_lop(:id);
                """;

        @SuppressWarnings("unchecked")
        List<KetQuaHollandPro> pros = em.createNativeQuery(sql, KetQuaHollandPro.class)
                .setParameter("id", lopId)
                .getResultList();

        return pros.stream().map(mapper::toRes).toList();
    }

    public List<KetQuaHollandRes> layDsHollandTheoHs(Long hsId) {
        String sql = """
                select * from school.lay_ds_kq_holland_theo_hs(:id);
                """;

        @SuppressWarnings("unchecked")
        List<KetQuaHollandPro> pros = em.createNativeQuery(sql, KetQuaHollandPro.class)
                .setParameter("id", hsId)
                .getResultList();

        return pros.stream().map(mapper::toRes).toList();
    }


    public ThongKeHollandTheoLopRes thongKeHolland(Long lopId) {
        String sql = """
                SELECT
                    lop_id::bigint AS lop_id,
                    so_luong_hoc_sinh::int AS so_luong_hoc_sinh,
                    top2_r::int AS top2_r,
                    top2_i::int AS top2_i,
                    top2_a::int AS top2_a,
                    top2_s::int AS top2_s,
                    top2_e::int AS top2_e,
                    top2_c::int AS top2_c
                FROM school.fn_thong_ke_holland_top_2_theo_lop(:id);
                
                """;

        ThongKeHollandTheoLopPro pro = (ThongKeHollandTheoLopPro) em.createNativeQuery(sql, ThongKeHollandTheoLopPro.class)
                .setParameter("id", lopId)
                .getSingleResult();


        return mapper.toRes(pro);
    }


    public KetQuaHollandRes lamBai(Long userId, KetQuaHollandReq req) {
        String sql = """
                select * from school.fn_them_ket_qua_holland(
                              p_user_id := :userId,
                              p_diem_r := :diemR,
                              p_diem_i := :diemI,
                              p_diem_a := :diemA, 
                              p_diem_s := :diemS, 
                              p_diem_e := :diemE, 
                              p_diem_c := :diemC, 
                              p_thoi_gian_lam := :thoiGianLam
                );
                """;

        KetQuaHollandPro pro = (KetQuaHollandPro) em.createNativeQuery(sql, KetQuaHollandPro.class)
                .setParameter("userId", userId)
                .setParameter("diemR", req.getDiemR())
                .setParameter("diemI", req.getDiemI())
                .setParameter("diemA", req.getDiemA())
                .setParameter("diemS", req.getDiemS())
                .setParameter("diemE", req.getDiemE())
                .setParameter("diemC", req.getDiemC())
                .setParameter("thoiGianLam", req.getThoiGianLam())
                .getSingleResult();

        return mapper.toRes(pro);
    }
}
