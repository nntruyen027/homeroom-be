package com.vinhthanh2.lophocdientu.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRes {
    String token;

    UserFullRes user;
}
