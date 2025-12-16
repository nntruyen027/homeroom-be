package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class GiaoVienPro {
    private Long id;
    private String username;
    private String hoTen;
    private String avatar;
    private String boMon;
    private String chucVu;
    private Date ngaySinh;
    private Boolean laNam;
    private Long xaId;
    private String tenXa;
    private Long tinhId;
    private String tenTinh;
    private String diaChiChiTiet;
}
