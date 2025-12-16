package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class XaPro {
    private Long id;

    private String ten;

    private String ghiChu;

    private Long tinhId;

    private String tenTinh;
}
