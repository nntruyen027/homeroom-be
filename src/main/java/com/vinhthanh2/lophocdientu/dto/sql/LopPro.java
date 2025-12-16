package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LopPro {
    private Long id;

    private String ten;

    private String hinhAnh;

    private Long truongId;

    private String tenTruong;

    private Long giaoVienId;

    private String tenGiaoVien;
}
