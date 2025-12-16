package com.vinhthanh2.lophocdientu.dto.res;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherRes {
    private Long id;
    private String username;
    private String hoTen;
    private String avatar;
    private String boMon;
    private String chucVu;
    private Date ngaySinh;
    private Boolean laNam;
    private XaRes xa;
    private String diaChiChiTiet;
}
