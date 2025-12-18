package com.vinhthanh2.lophocdientu.dto.sql;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vinhthanh2.lophocdientu.dto.res.UserDaXemDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class ThongBaoViewPro {

    private Long thongBaoId;
    private String tieuDe;
    private String noiDung;
    private Long lopId;
    private Instant thoiGianTao;

    @JsonProperty("ds_user_da_xem")
    private List<UserDaXemDto> dsUserDaXem;
    private Long nguoiTaoId;
}
