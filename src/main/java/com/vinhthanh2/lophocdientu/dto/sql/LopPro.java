package com.vinhthanh2.lophocdientu.dto.sql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LopPro {
    @JsonProperty("out_id")
    private Long outId;

    private String ten;

    @JsonProperty("hinh_anh")
    private String hinhAnh;

    @JsonProperty("truong_id")
    private Long truongId;

    @JsonProperty("ten_truong")
    private String tenTruong;

    @JsonProperty("giao_vien_id")
    private Long giaoVienId;

    @JsonProperty("ten_giao_vien")
    private String tenGiaoVien;
}
