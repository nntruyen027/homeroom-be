package com.vinhthanh2.lophocdientu.dto.req;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTeacherReq {
    // Dành cho giáo viên
    private String boMon;
    private String chucVu;
    private String avatar;
    private String hoTen;
    private Long xaId;
    private String diaChiChiTiet;
    private Boolean laNam;
    private LocalDate ngaySinh;
}
