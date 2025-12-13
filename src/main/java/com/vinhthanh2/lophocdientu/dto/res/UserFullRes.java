package com.vinhthanh2.lophocdientu.dto.res;

import com.vinhthanh2.lophocdientu.entity.Lop;
import com.vinhthanh2.lophocdientu.entity.Xa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullRes {
    private Long id;

    private String username;

    private String avatar;

    private List<String> roles;

    private List<String> permissions;

    private String hoTen;

    // ===== ĐỊA CHỈ =====
    private Long xaId;

    private Long lopId;

    private String diaChiChiTiet;

    private Xa xa;

    private Lop lop;

    // ===== THÔNG TIN CÁ NHÂN =====
    private String ngaySinh;

    private Boolean laNam;

    private String soThich;

    private String monHocYeuThich;

    private String diemManh;

    private String diemYeu;

    private String ngheNghiepMongMuon;

    private String nhanXetGiaoVien;

    private String ghiChu;

    // ===== RIASEC =====
    private Integer realisticScore;

    private Integer investigativeScore;

    private Integer artisticScore;

    private Integer socialScore;

    private Integer enterprisingScore;

    private Integer conventionalScore;

    private String assessmentResult;

    // ===== PHỤ HUYNH =====
    private String tenCha;

    private String nsCha;

    private String sdtCha;

    private String tenMe;

    private String nsMe;

    private String sdtMe;

    private String tenPhKhac;

    private String nsPhKhac;

    private String sdtPhKhac;

    // ===== GIÁO VIÊN =====
    private String boMon;

    private String chucVu;
}
