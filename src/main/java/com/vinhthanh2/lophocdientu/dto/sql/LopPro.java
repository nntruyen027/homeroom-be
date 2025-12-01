package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LopPro {
    private Long outId;

    private String ten;

    private String hinhAnh;

    private Long truongId;

    private String tenTruong;

    private Long giaoVienId;

    private String tenGiaoVien;
}
