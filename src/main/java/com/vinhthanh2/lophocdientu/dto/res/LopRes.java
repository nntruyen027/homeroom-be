package com.vinhthanh2.lophocdientu.dto.res;

import lombok.Data;

@Data
public class LopRes {
    private Long id;

    private String ten;

    private String hinhAnh;

    private TruongRes truong;
    private Long giaoVienId;

    private String tenGiaoVien;
}
