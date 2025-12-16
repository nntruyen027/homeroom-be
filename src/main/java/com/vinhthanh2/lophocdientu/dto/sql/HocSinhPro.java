package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HocSinhPro {

    // ===== auth.users =====
    private Long userId;          // u.id AS user_id
    private String username;      // u.username
    private String hoTen;          // u.ho_ten
    private String avatar;         // u.avatar

    // ===== school.lop / truong =====
    private Long lopId;            // hs.lop_id
    private String tenLop;         // l.ten AS ten_lop
    private Long truongId;         // l.truong_id
    private String tenTruong;      // tr.ten AS ten_truong

    // ===== auth.hoc_sinh =====
    private String soThich;        // hs.so_thich
    private String monHocYeuThich; // hs.mon_hoc_yeu_thich
    private String diemManh;       // hs.diem_manh
    private String diemYeu;        // hs.diem_yeu
    private String ghiChu;         // hs.ghi_chu

    // ===== thong_tin_nguoi_dung =====
    private Date ngaySinh;         // tt.ngay_sinh
    private Boolean laNam;         // tt.la_nam
    private Long xaId;             // tt.xa_id
    private String tenXa;          // x.ten AS ten_xa
    private Long tinhId;           // x.tinh_id
    private String tenTinh;        // ti.ten AS ten_tinh
    private String diaChiChiTiet;  // tt.dia_chi_chi_tiet
}
