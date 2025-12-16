package com.vinhthanh2.lophocdientu.dto.req;

import lombok.Data;

@Data
public class UpdateStudentReq {
    private Long username;

    private String avatar;
    private String hoTen;

    // Học sinh thuộc lớp nào
    private Long lopId;


    // === Thông tin thêm ===
    private String ngaySinh;
    private Boolean laNam;
    private String soThich;
    private String monHocYeuThich;
    private String diemManh;
    private String diemYeu;
    private String ghiChu;
}
