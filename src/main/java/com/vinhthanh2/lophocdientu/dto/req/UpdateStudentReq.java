package com.vinhthanh2.lophocdientu.dto.req;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateStudentReq {
    private String username;

    private String avatar;
    private String hoTen;

    // Học sinh thuộc lớp nào
    private Long lopId;
    private Long xaId;


    // === Thông tin thêm ===
    private Date ngaySinh;
    private Boolean laNam;
    private String soThich;
    private String monHocYeuThich;
    private String diemManh;
    private String diemYeu;
    private String ghiChu;
    private String diaChiChiTiet;
}
