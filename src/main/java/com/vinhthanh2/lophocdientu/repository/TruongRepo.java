package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.TruongReq;
import com.vinhthanh2.lophocdientu.dto.res.TruongRes;
import com.vinhthanh2.lophocdientu.dto.sql.TruongPro;
import com.vinhthanh2.lophocdientu.mapper.TruongMapper;
import com.vinhthanh2.lophocdientu.util.ExcelBatchImporter;
import com.vinhthanh2.lophocdientu.util.ExcelUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Repository
@AllArgsConstructor
public class TruongRepo {
    @PersistenceContext
    private EntityManager entityManager;
    private final TruongMapper truongMapper;

    @SuppressWarnings("unchecked")
    public List<TruongRes> layTatCaTruong(String search, int page, int size) {
        int offset = (page - 1) * size;

        String sql = """
                select * from school.fn_lay_tat_ca_truong(
                    :p_search,
                    :p_offset,
                    :p_limit
                )
                """;

        List<TruongPro> truongPros = entityManager.createNativeQuery(sql, TruongPro.class)
                .setParameter("p_search", search)
                .setParameter("p_offset", offset)
                .setParameter("p_limit", size)
                .getResultList();

        return truongPros.stream().map(truongMapper::toDto).toList();
    }

    public Long demTatCaTruong(String search) {
        String sql = """
                    SELECT school.fn_dem_tat_ca_truong(:p_search)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_search", search)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }

    @Transactional
    public TruongRes taoTruong(TruongReq truong) {
        String sql = """
                    SELECT * from school.fn_tao_truong(
                        :p_ten,
                        :p_xa_id,
                        :p_dia_chi_chi_tiet,
                        :p_hinh_anh,
                        :p_logo
                    )
                """;

        return truongMapper.toDto((TruongPro) entityManager.createNativeQuery(sql, TruongPro.class)
                .setParameter("p_ten", truong.getTen())
                .setParameter("p_dia_chi_chi_tiet", truong.getDiaChiChiTiet())
                .setParameter("p_xa_id", truong.getXaId())
                .setParameter("p_hinh_anh", truong.getHinhAnh())
                .setParameter("p_logo", truong.getLogo())
                .getSingleResult()
        );
    }

    @Transactional
    public TruongRes suaTruong(Long id, TruongReq truong) {
        String sql = """
                    SELECT * from school.fn_sua_truong(
                        :p_id,
                        :p_ten,
                        :p_xa_id,
                        :p_dia_chi_chi_tiet,
                        :p_hinh_anh,
                        :p_logo
                    )
                """;

        return truongMapper.toDto((TruongPro) entityManager.createNativeQuery(sql, TruongPro.class)
                .setParameter("p_id", id)
                .setParameter("p_ten", truong.getTen())
                .setParameter("p_dia_chi_chi_tiet", truong.getDiaChiChiTiet())
                .setParameter("p_xa_id", truong.getXaId())
                .setParameter("p_hinh_anh", truong.getHinhAnh())
                .setParameter("p_logo", truong.getLogo())
                .getSingleResult()
        );
    }

    @Transactional
    public boolean xoaTruong(Long id) {
        String sql = """
                    SELECT school.fn_xoa_truong(:p_id::bigint)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_id::bigint", id)
                .getSingleResult();

        if (result instanceof Boolean b) return b;
        return Boolean.parseBoolean(result.toString());
    }

    @Transactional
    public void importTruong(MultipartFile file) throws IOException {
        ExcelBatchImporter batchImporter = new ExcelBatchImporter(entityManager);
        int batchSize = 100;

        batchImporter.importExcelBatch(
                file,
                batchSize,
                row -> {
                    String ten = ExcelUtils.getCellValue(row.getCell(1));
                    String diaChiChiTiet = ExcelUtils.getCellValue(row.getCell(3));

                    String xaRaw = ExcelUtils.getCellValue(row.getCell(2));
                    Long xaId = null;
                    if (xaRaw != null && xaRaw.contains(" - ")) {
                        xaId = Long.parseLong(xaRaw.split(" - ")[0]);
                    } else {
                        throw new RuntimeException("Sai định dạng cột xã ở dòng " + (row.getRowNum() + 1));
                    }

                    String hinhAnh = ExcelUtils.getCellValue(row.getCell(4));
                    String logo = ExcelUtils.getCellValue(row.getCell(5));

                    if (ten == null || ten.isEmpty()) {
                        throw new RuntimeException("Tên trường bị trống ở dòng " + (row.getRowNum() + 1));
                    }

                    return new Object[]{ten, diaChiChiTiet, xaId, hinhAnh, logo};
                },
                "school.fn_import_truong", "school.truong_input"
        );
    }
}
