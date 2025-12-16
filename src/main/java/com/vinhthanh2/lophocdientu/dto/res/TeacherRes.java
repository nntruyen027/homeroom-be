package com.vinhthanh2.lophocdientu.dto.res;

import lombok.Data;

@Data
public class TeacherRes {
    private Long userId;
    private String username;
    private String hoTen;
    private String avatar;
    private String boMon;
    private String chucVu;
    private String ngaySinh;
    private Boolean laNam;
    private XaRes xa;
    private String diaChiChiTiet;
}
