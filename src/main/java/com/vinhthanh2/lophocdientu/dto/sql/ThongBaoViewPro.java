package com.vinhthanh2.lophocdientu.dto.sql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ThongBaoViewPro {

    private Long thongBaoId;
    private String tieuDe;
    private String noiDung;
    private Long lopId;
    private Timestamp thoiGianTao;

    @JsonProperty("ds_user_da_xem")
    private String dsUserDaXem;
    private Long nguoiTaoId;
}
