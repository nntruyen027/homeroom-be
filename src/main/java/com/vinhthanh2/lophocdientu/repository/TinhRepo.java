package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.TinhReq;
import com.vinhthanh2.lophocdientu.dto.res.TinhRes;
import com.vinhthanh2.lophocdientu.dto.sql.TinhPro;
import com.vinhthanh2.lophocdientu.mapper.TinhMapper;
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
public class TinhRepo {
    @PersistenceContext
    private EntityManager entityManager;
    private final TinhMapper tinhMapper;

    @SuppressWarnings("unchecked")
    public List<TinhRes> layTatCaTinh(String search, int page, int size) {
        int offset = (page - 1) * size;

        String sql = """
                select * from dm_chung.fn_lay_tat_ca_tinh(
                    :p_search,
                    :p_offset,
                    :p_limit
                )
                """;

        List<TinhPro> tinhPros = entityManager.createNativeQuery(sql, TinhPro.class)
                .setParameter("p_search", search)
                .setParameter("p_offset", offset)
                .setParameter("p_limit", size)
                .getResultList();

        return tinhPros.stream().map(tinhMapper::toDto).toList();
    }

    public Long demTatCaTinh(String search) {
        String sql = """
                    SELECT dm_chung.fn_dem_tat_ca_tinh(:p_search)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_search", search)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }

    @Transactional
    public TinhRes taoTinh(TinhReq tinh) {
        String sql = """
                    SELECT * from dm_chung.fn_tao_tinh(
                        :p_ten,
                        :p_ghi_chu
                    )
                """;

        return tinhMapper.toDto((TinhPro) entityManager.createNativeQuery(sql, TinhPro.class)
                .setParameter("p_ten", tinh.getTen())
                .setParameter("p_ghi_chu", tinh.getGhiChu())
                .getSingleResult()
        );
    }

    @Transactional
    public TinhRes suaTinh(Long id, TinhReq tinh) {
        String sql = """
                    SELECT * from dm_chung.fn_sua_tinh(
                        :p_id,
                        :p_ten,
                        :p_ghi_chu
                    )
                """;

        return tinhMapper.toDto((TinhPro) entityManager.createNativeQuery(sql, TinhPro.class)
                .setParameter("p_id", id)
                .setParameter("p_ten", tinh.getTen())
                .setParameter("p_ghi_chu", tinh.getGhiChu())
                .getSingleResult()
        );
    }

    @Transactional
    public boolean xoaTinh(Long id) {
        String sql = """
                    SELECT dm_chung.fn_xoa_tinh(:p_id::bigint)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_id::bigint", id)
                .getSingleResult();

        if (result instanceof Boolean b) return b;
        return Boolean.parseBoolean(result.toString());
    }

    @Transactional
    public void importTinh(MultipartFile file) throws IOException {
        ExcelBatchImporter batchImporter = new ExcelBatchImporter(entityManager);
        int batchSize = 100;

        batchImporter.importExcelBatch(
                file,
                batchSize,
                row -> {
                    String ten = ExcelUtils.getCellValue(row.getCell(1));
                    String ghiChu = ExcelUtils.getCellValue(row.getCell(2));

                    if (ten == null || ten.isEmpty()) {
                        throw new RuntimeException("Tên tỉnh bị trống ở dòng " + (row.getRowNum() + 1));
                    }


                    return new Object[]{ten, ghiChu};
                },
                "dm_chung.fn_import_tinh", "dm_chung.tinh_input"
        );
    }
}
