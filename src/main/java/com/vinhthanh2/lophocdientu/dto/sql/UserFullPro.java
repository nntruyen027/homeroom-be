package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullPro {

    private Long id;

    private String username;

    private String hoTen;

    private String avatar;

    private boolean isActive;

    private Date createdAt;

    private String[] roles;

    private String[] permissions;
}
