package com.vinhthanh2.lophocdientu.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KetQuaHollandReq {

    private Integer diemR;
    private Integer diemI;
    private Integer diemA;
    private Integer diemS;
    private Integer diemE;
    private Integer diemC;


    private Integer thoiGianLam;
}
