package com.vinhthanh2.lophocdientu.dto.req;

import lombok.Data;

@Data
public class TeacherRegisterReq {
    private String username;

    private String password;

    private String repeatPass;

    private String hoTen;

    private String ngaySinh;
    private Boolean laNam;

    private Long xaId;

    private String diaChiChiTiet;
    // Dành cho giáo viên
    private String boMon;
    private String chucVu;
}
