package com.vinhthanh2.lophocdientu.dto.req;

import lombok.Data;

@Data
public class AdminRequest {
    String hoTen;
    String avatar;
    String userName;
    String password;
    String repeatPassword;
}
