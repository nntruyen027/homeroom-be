package com.vinhthanh2.lophocdientu.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class HdHuongNghiepReq {

    private String tenHoatDong;

    private LocalDate thoiGianBatDau;
    private LocalDate thoiGianKetThuc;

    private String ghiChu;
}