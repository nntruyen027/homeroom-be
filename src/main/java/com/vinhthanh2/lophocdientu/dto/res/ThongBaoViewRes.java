package com.vinhthanh2.lophocdientu.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class ThongBaoViewRes {

    private Long thongBaoId;
    private String tieuDe;
    private String noiDung;
    private Long lopId;
    private Instant thoiGianTao;

    @JsonProperty("ds_user_da_xem")
    private List<UserDaXemDto> dsUserDaXem;
    private Long nguoiTaoId;
}
