package com.vinhthanh2.lophocdientu.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class KetQuaHollandRes {
    private Long id;
    private Long hsId;
    private String hoTen;

    private Integer diemR;
    private Integer diemI;
    private Integer diemA;
    private Integer diemS;
    private Integer diemE;
    private Integer diemC;


    private String maHolland;

    private Integer thoiGianLam;
    private LocalDateTime ngayDanhGia;
}
