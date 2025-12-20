package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class KetQuaHollandPro {
    private Long id;
    private Long hsId;
    private String hoTen;
    private Long lopId;

    private Integer diemR;
    private Integer diemI;
    private Integer diemA;
    private Integer diemS;
    private Integer diemE;
    private Integer diemC;

    private String maHolland;

    private Integer thoiGianLam;
    private Timestamp ngayDanhGia;
}
