package com.vinhthanh2.lophocdientu.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullRes {
    private Long id;

    private String username;

    private String hoTen;

    private boolean isActive;

    private Date createdAt;

    private List<String> roles;

    private List<String> permissions;


}
