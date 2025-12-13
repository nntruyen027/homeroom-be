package com.vinhthanh2.lophocdientu.dto.res;

import lombok.Data;

import java.util.List;

@Data
public class RoleRes {
    private Long id;
    private String name;
    private String code;
    private List<String> permissions;
}
