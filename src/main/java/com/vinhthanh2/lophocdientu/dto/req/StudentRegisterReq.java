package com.vinhthanh2.lophocdientu.dto.req;

import lombok.Data;

@Data
public class StudentRegisterReq {
    private String username;

    private String password;

    private String hoTen;

    private Long lopId;
    private Long xaId;

    private String diaChiChiTiet;

    // === Thông tin thêm ===
    private String ngaySinh;
    private Boolean laNam;
}
