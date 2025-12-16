package com.vinhthanh2.lophocdientu.dto.res;

import lombok.Data;

import java.util.Date;

@Data
public class HocSinhRes {
    private Long id;

    private String username;

    private String avatar;

    private String hoTen;

    private LopRes lop;

    private XaRes xa;

    private String diaChiChiTiet;

    private String soThich;

    private String monHocYeuThich;

    private String diemManh;

    private String diemYeu;

    private Date ngaySinh;

    private boolean laNam;
}
