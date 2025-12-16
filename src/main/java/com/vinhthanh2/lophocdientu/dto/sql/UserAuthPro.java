package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthPro {
    private Long id;
    private String username;
    private String password;
    private String[] roles;
    private String[] permissions;
}
