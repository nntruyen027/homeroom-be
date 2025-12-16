package com.vinhthanh2.lophocdientu.dto.req;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRegisterReq {
    private String username;

    private String password;

    private String hoTen;

    private Long lopId;
    private Long xaId;

    private String diaChiChiTiet;

    // === Thông tin thêm ===
    private LocalDate ngaySinh;
    private Boolean laNam;
}
