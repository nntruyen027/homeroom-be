package com.vinhthanh2.lophocdientu.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDaXemDto {
    private Long id;
    private String ten;
    private String avatar;
}
