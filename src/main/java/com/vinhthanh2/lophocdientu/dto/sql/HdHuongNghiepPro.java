package com.vinhthanh2.lophocdientu.dto.sql;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HdHuongNghiepPro {
    private Long id;
    private String tenHoatDong;

    private Date thoiGianBatDau;
    private Date thoiGianKetThuc;

    private String ghiChu;

    private Long nguoiTaoId;
    private String nguoiTaoUsername;
    private String nguoiTaoHoTen;

    private Long[] dsLopId;
}
