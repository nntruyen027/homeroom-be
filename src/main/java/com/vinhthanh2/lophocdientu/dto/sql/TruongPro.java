package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TruongPro {
    private Long id;

    private String ten;

    private Long xaId;

    private String tenXa;

    private Long tinhId;

    private String tenTinh;

    private String diaChiChiTiet;

    private String hinhAnh;

    private String logo;
}
