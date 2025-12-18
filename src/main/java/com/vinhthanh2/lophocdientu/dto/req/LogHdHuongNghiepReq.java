package com.vinhthanh2.lophocdientu.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogHdHuongNghiepReq {
    private String nnQuanTam;       // nội dung quan tâm
    private String kyNangHanChe;    // kỹ năng hạn chế
    private Integer mucDoHieuBiet;  // mức độ hiểu biết
    private String caiThien;        // cải thiện
    private Long idHoatDong;        // hd_id
}
