package com.vinhthanh2.lophocdientu.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanXetLogHdHnReq {
    private Long hsId;
    private Long logId;
    private String nhanXet;
}
