package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.XaReq;
import com.vinhthanh2.lophocdientu.dto.res.XaRes;
import com.vinhthanh2.lophocdientu.dto.sql.XaPro;
import com.vinhthanh2.lophocdientu.mapper.XaMapper;
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
public class XaRepo {
    @PersistenceContext
    private EntityManager entityManager;
    private final XaMapper xaMapper;

    @SuppressWarnings("unchecked")
    public List<XaRes> layTatCaXa(String search, Long tinhId, int page, int size) {
        int offset = (page - 1) * size;

        String sql = """
                select * from dm_chung.fn_lay_tat_ca_xa(
                    :p_search,
                    :p_tinh_id,
                    :p_offset,
                    :p_limit
                )
                """;


        List<XaPro> xaPros = entityManager.createNativeQuery(sql, XaPro.class)
                .setParameter("p_search", search)
                .setParameter("p_tinh_id", tinhId)
                .setParameter("p_offset", offset)
                .setParameter("p_limit", size)
                .getResultList();

        return xaPros.stream().map(xaMapper::toDto).toList();
    }

    public Long demTatCaXa(String search, Long tinhId) {
        String sql = """
                    SELECT dm_chung.fn_dem_tat_ca_xa(:p_search, :p_tinh_id)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_search", search)
                .setParameter("p_tinh_id", tinhId)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }

    @Transactional
    public XaRes taoXa(XaReq xa) {
        String sql = """
                    SELECT * from dm_chung.fn_tao_xa(
                        :p_ten,
                        :p_ghi_chu,
                        :p_tinh_id
                    )
                """;

        return xaMapper.toDto((XaPro) entityManager.createNativeQuery(sql, XaPro.class)
                .setParameter("p_ten", xa.getTen())
                .setParameter("p_ghi_chu", xa.getGhiChu())
                .setParameter("p_tinh_id", xa.getTinhId())
                .getSingleResult()
        );
    }

    @Transactional
    public XaRes suaXa(Long id, XaReq xa) {
        String sql = """
                    SELECT * from dm_chung.fn_sua_xa(
                        :p_id,
                        :p_ten,
                        :p_ghi_chu,
                        :p_tinh_id
                    )
                """;

        return xaMapper.toDto((XaPro) entityManager.createNativeQuery(sql, XaPro.class)
                .setParameter("p_id", id)
                .setParameter("p_ten", xa.getTen())
                .setParameter("p_ghi_chu", xa.getGhiChu())
                .setParameter("p_tinh_id", xa.getTinhId())
                .getSingleResult()
        );
    }

    @Transactional
    public boolean xoaXa(Long id) {
        String sql = """
                    SELECT dm_chung.fn_xoa_xa(:p_id)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_id", id)
                .getSingleResult();

        if (result instanceof Boolean b) return b;
        return Boolean.parseBoolean(result.toString());
    }

    @Transactional
    public void importXa(MultipartFile file) throws IOException {
        ExcelBatchImporter batchImporter = new ExcelBatchImporter(entityManager);
        int batchSize = 100;

        batchImporter.importExcelBatch(
                file,
                batchSize,
                row -> {
                    String ten = ExcelUtils.getCellValue(row.getCell(1));
                    String ghiChu = ExcelUtils.getCellValue(row.getCell(3));
                    String tinhId = ExcelUtils.getCellValue(row.getCell(2)).split(" - ")[0];

                    if (ten == null || ten.isEmpty()) {
                        throw new RuntimeException("Tên xã bị trống ở dòng " + (row.getRowNum() + 1));
                    }
                    if (tinhId == null || tinhId.isEmpty()) {
                        throw new RuntimeException("Tên tỉnh bị trống ở dòng " + (row.getRowNum() + 1));
                    }

                    return new Object[]{ten, ghiChu, tinhId};
                },
                "dm_chung.fn_import_xa", "dm_chung.xa_input"
        );
    }
}
