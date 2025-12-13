package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RolePro {
    private Long id;
    private String name;
    private String code;
    private String[] permissions;

}
