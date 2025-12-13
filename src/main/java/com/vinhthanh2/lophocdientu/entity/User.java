package com.vinhthanh2.lophocdientu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users", schema = "auth")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 120)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String avatar;

    @Transient
    private List<String> roles;

    @Transient
    private List<String> permissions;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    // ===== ĐỊA CHỈ =====
    @Column(name = "xa_id")
    private Long xaId;

    @Column(name = "lop_id")
    private Long lopId;

    @Column(name = "dia_chi_chi_tiet")
    private String diaChiChiTiet;

    @Transient
    private Xa xa;

    @Transient
    private Lop lop;

    // ===== THÔNG TIN CÁ NHÂN =====
    @Column(name = "ngay_sinh")
    private String ngaySinh;

    @Column(name = "la_nam")
    private Boolean laNam;

    @Column(name = "so_thich")
    private String soThich;

    @Column(name = "mon_hoc_yeu_thich")
    private String monHocYeuThich;

    @Column(name = "diem_manh")
    private String diemManh;

    @Column(name = "diem_yeu")
    private String diemYeu;

    @Column(name = "nghe_nghiep_mong_muon")
    private String ngheNghiepMongMuon;

    @Column(name = "nhan_xet_giao_vien")
    private String nhanXetGiaoVien;

    @Column(name = "ghi_chu")
    private String ghiChu;

    // ===== RIASEC =====
    @Column(name = "realistic_score")
    private Integer realisticScore;

    @Column(name = "investigative_score")
    private Integer investigativeScore;

    @Column(name = "artistic_score")
    private Integer artisticScore;

    @Column(name = "social_score")
    private Integer socialScore;

    @Column(name = "enterprising_score")
    private Integer enterprisingScore;

    @Column(name = "conventional_score")
    private Integer conventionalScore;

    @Column(name = "assessment_result", length = 500)
    private String assessmentResult;

    // ===== PHỤ HUYNH =====
    @Column(name = "ten_cha")
    private String tenCha;

    @Column(name = "ns_cha")
    private String nsCha;

    @Column(name = "sdt_cha")
    private String sdtCha;

    @Column(name = "ten_me")
    private String tenMe;

    @Column(name = "ns_me")
    private String nsMe;

    @Column(name = "sdt_me")
    private String sdtMe;

    @Column(name = "ten_ph_khac")
    private String tenPhKhac;

    @Column(name = "ns_ph_khac")
    private String nsPhKhac;

    @Column(name = "sdt_ph_khac")
    private String sdtPhKhac;

    // ===== GIÁO VIÊN =====
    @Column(name = "bo_mon")
    private String boMon;

    @Column(name = "chuc_vu")
    private String chucVu;
}
