package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogHdHuongNghiepPro {
    private String nnQuanTam;       // nội dung quan tâm
    private String kyNangHanChe;    // kỹ năng hạn chế
    private Integer mucDoHieuBiet;  // mức độ hiểu biết
    private String caiThien;
    private String nhanXet;

    private Long idHocSinh;         // user_id
    private Long idHoatDong;
    private Long idGiaoVien;// hd_id

    private String tenHoatDong;     // tên hoạt động
    private String tenHocSinh;
    private String tenGiaoVien;     // tên học sinh
}
