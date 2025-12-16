package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.RoleRes;
import com.vinhthanh2.lophocdientu.dto.sql.RolePro;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface RoleMapper {

    RoleRes fromPro(RolePro dto);
}
