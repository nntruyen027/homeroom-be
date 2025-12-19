package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.StudentRegisterReq;
import com.vinhthanh2.lophocdientu.dto.req.UpdateStudentReq;
import com.vinhthanh2.lophocdientu.dto.res.HocSinhRes;
import com.vinhthanh2.lophocdientu.dto.sql.HocSinhPro;
import com.vinhthanh2.lophocdientu.mapper.HocSinhMapper;
import com.vinhthanh2.lophocdientu.util.ExcelBatchImporter;
import com.vinhthanh2.lophocdientu.util.ExcelUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Repository
@AllArgsConstructor
public class HocSinhRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private final HocSinhMapper hocSinhMapper;

    private final PasswordEncoder passwordEncoder;

    @SuppressWarnings("unchecked")
    public List<HocSinhRes> layTatCaHocSinh(Long lopId, String search, int page, int size) {
        int offset = (page - 1) * size;

        String sql = """
                    SELECT * FROM auth.fn_lay_tat_ca_hoc_sinh(
                        :p_lop_id, :p_search, :p_offset, :p_limit
                    )
                """;

        List<HocSinhPro> pros = entityManager.createNativeQuery(sql, HocSinhPro.class)
                .setParameter("p_lop_id", lopId)
                .setParameter("p_search", search)
                .setParameter("p_offset", offset)
                .setParameter("p_limit", size)
                .getResultList().stream().toList();

        return pros.stream().map(hocSinhMapper::toHocSinhRes).toList();
    }

    public HocSinhRes layHsTheoId(Long userId) {

        String sql = """
                    SELECT * FROM auth.fn_lay_hs_theo_id(
                        :p_id
                    )
                """;

        HocSinhPro pro = (HocSinhPro) entityManager.createNativeQuery(sql, HocSinhPro.class)
                .setParameter("p_id", userId)
                .getSingleResult();

        return hocSinhMapper.toHocSinhRes(pro);
    }

    // ============================================================
    // ĐẾM HỌC SINH THEO LỚP
    // ============================================================
    public Long demHocSinhTheoLop(Long lopId, String search) {
        String sql = """
                    SELECT auth.fn_dem_tat_ca_hoc_sinh(:p_lop_id, :p_search)
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
    // TẠO HỌC SINH (gọi function SQL)
    // ============================================================
    @Transactional
    public HocSinhRes taoHocSinh(StudentRegisterReq req) {

        String sql = """
                    SELECT * FROM auth.fn_tao_hoc_sinh(
                        :p_username,
                        :p_password,
                        :p_ho_ten,
                        :p_lop_id,
                        :p_ngay_sinh,
                        :p_la_nam,
                        :p_xa_id,
                        :p_dia_chi
                    )
                """;

        return hocSinhMapper.toHocSinhRes((HocSinhPro) entityManager.createNativeQuery(sql, HocSinhPro.class)
                .setParameter("p_username", req.getUsername())
                .setParameter("p_password", req.getPassword())
                .setParameter("p_ho_ten", req.getHoTen())
                .setParameter("p_lop_id", req.getLopId())
                .setParameter("p_ngay_sinh", req.getNgaySinh())
                .setParameter("p_la_nam", req.getLaNam())
                .setParameter("p_xa_id", req.getXaId())
                .setParameter("p_dia_chi", req.getDiaChiChiTiet())
                .getSingleResult());
    }

    // ============================================================
    // SỬA HỌC SINH
    // ============================================================
    @Transactional
    public HocSinhRes suaHocSinh(Long id, UpdateStudentReq req) {

        String sql = """
                    SELECT * FROM auth.fn_sua_hoc_sinh(
                              :p_user_id,
                              :p_lop_id,
                              :p_ho_ten ,
                              :p_so_thich ,
                              :p_mon_hoc_yeu_thich ,
                              :p_diem_manh ,
                              :p_diem_yeu ,
                              :p_ghi_chu ,
                              :p_xa_id ,
                              :p_ngay_sinh ,
                              :p_la_nam ,
                              :p_dia_chi 
                    )
                """;

        return hocSinhMapper.toHocSinhRes((HocSinhPro) entityManager.createNativeQuery(sql, HocSinhPro.class)
                .setParameter("p_user_id", id)
                .setParameter("p_lop_id", req.getLopId())
                .setParameter("p_ho_ten", req.getHoTen())

                .setParameter("p_so_thich", req.getSoThich())
                .setParameter("p_mon_hoc_yeu_thich", req.getMonHocYeuThich())
                .setParameter("p_diem_manh", req.getDiemManh())
                .setParameter("p_diem_yeu", req.getDiemYeu())
                .setParameter("p_ghi_chu", req.getGhiChu())
                .setParameter("p_xa_id", req.getXaId())
                .setParameter("p_ngay_sinh", req.getNgaySinh())
                .setParameter("p_la_nam", req.getLaNam())
                .setParameter("p_dia_chi", req.getDiaChiChiTiet())

                .getSingleResult());
    }

    // ============================================================
    // XÓA HỌC SINH
    // ============================================================
    @Transactional
    public boolean xoaHocSinh(Long id) {

        String sql = """
                    SELECT auth.fn_xoa_nguoi_dung(:p_id)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_id", id)
                .getSingleResult();

        if (result instanceof Boolean b) return b;
        return Boolean.parseBoolean(result.toString());
    }

    @Transactional
    public void importHocSinh(Long lopId, MultipartFile file) throws IOException {
        ExcelBatchImporter batchImporter = new ExcelBatchImporter(entityManager);
        int batchSize = 100;

        batchImporter.importExcelBatch(
                file,
                batchSize,
                row -> {
                    String hoTen = ExcelUtils.getCellValue(row.getCell(1));
                    String username = ExcelUtils.getCellValue(row.getCell(2));
                    String password = ExcelUtils.getCellValue(row.getCell(3));
                    String ngaySinh = ExcelUtils.getCellValue(row.getCell(4));
                    String laNam = ExcelUtils.getCellValue(row.getCell(5));

                    if (hoTen == null || hoTen.isEmpty()) {
                        throw new RuntimeException("Tên học sinh bị trống ở dòng " + (row.getRowNum() + 1));
                    }
                    if (username == null || username.isEmpty()) {
                        throw new RuntimeException("Tên học sinh bị trống ở dòng " + (row.getRowNum() + 1));
                    }
                    if (password == null || password.isEmpty()) {
                        throw new RuntimeException("Tên học sinh bị trống ở dòng " + (row.getRowNum() + 1));
                    }


                    return new Object[]{username, passwordEncoder.encode(password), hoTen, lopId, ngaySinh, laNam};
                },
                "auth.fn_import_hoc_sinh", "auth.hoc_sinh_input"
        );
    }
}
