package com.vinhthanh2.lophocdientu.dto.sql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullPro {

    // ===== CORE =====
    @JsonProperty("out_id")
    private Long outId;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("ho_ten")
    private String hoTen;

    // ===== ĐỊA CHỈ =====
    @JsonProperty("xa_id")
    private Long xaId;
    @JsonProperty("ten_xa")
    private String tenXa;
    @JsonProperty("tinh_id")
    private Long tinhId;
    @JsonProperty("ten_tinh")
    private String tenTinh;
    @JsonProperty("dia_chi_chi_tiet")
    private String diaChiChiTiet;

    // ===== LỚP – TRƯỜNG =====
    @JsonProperty("lop_id")
    private Long lopId;
    @JsonProperty("ten_lop")
    private String tenLop;
    @JsonProperty("truong_id")
    private Long truongId;
    @JsonProperty("ten_truong")
    private String tenTruong;

    // ===== ROLE =====
    @JsonProperty("roles")
    private String roles;

    // ===== THÔNG TIN CÁ NHÂN =====
    @JsonProperty("ngay_sinh")
    private String ngaySinh;
    @JsonProperty("la_nam")
    private Boolean laNam;
    @JsonProperty("so_thich")
    private String soThich;
    @JsonProperty("mon_hoc_yeu_thich")
    private String monHocYeuThich;
    @JsonProperty("diem_manh")
    private String diemManh;
    @JsonProperty("diem_yeu")
    private String diemYeu;
    @JsonProperty("nghe_nghiep_mong_muon")
    private String ngheNghiepMongMuon;
    @JsonProperty("nhan_xet_giao_vien")
    private String nhanXetGiaoVien;
    @JsonProperty("ghi_chu")
    private String ghiChu;

    // ===== RIASEC =====
    @JsonProperty("realistic_score")
    private Integer realisticScore;
    @JsonProperty("investigative_score")
    private Integer investigativeScore;
    @JsonProperty("artistic_score")
    private Integer artisticScore;
    @JsonProperty("social_score")
    private Integer socialScore;
    @JsonProperty("enterprising_score")
    private Integer enterprisingScore;
    @JsonProperty("conventional_score")
    private Integer conventionalScore;
    @JsonProperty("assessment_result")
    private String assessmentResult;

    // ===== PHỤ HUYNH =====
    @JsonProperty("ten_cha")
    private String tenCha;
    @JsonProperty("ns_cha")
    private String nsCha;
    @JsonProperty("sdt_cha")
    private String sdtCha;
    @JsonProperty("ten_me")
    private String tenMe;
    @JsonProperty("ns_me")
    private String nsMe;
    @JsonProperty("sdt_me")
    private String sdtMe;
    @JsonProperty("ten_ph_khac")
    private String tenPhKhac;
    @JsonProperty("ns_ph_khac")
    private String nsPhKhac;
    @JsonProperty("sdt_ph_khac")
    private String sdtPhKhac;

    // ===== GIÁO VIÊN =====
    @JsonProperty("bo_mon")
    private String boMon;
    @JsonProperty("chuc_vu")
    private String chucVu;

    // ===== PERMISSIONS =====
    @JsonProperty("permissions")
    private String permissions;
}
