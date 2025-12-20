package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongKeHollandTheoLopPro {
    private Long lopId;             // lop_id
    private Integer soLuongHocSinh; // so_luong_hoc_sinh
    private Integer top2R;          // top2_r
    private Integer top2I;          // top2_i
    private Integer top2A;          // top2_a
    private Integer top2S;          // top2_s
    private Integer top2E;          // top2_e
    private Integer top2C;          // top2_c
}
