package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.RoleRes;
import com.vinhthanh2.lophocdientu.dto.sql.RolePro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface RoleMapper {
    @Mapping(source = "id", target = "id")
    RoleRes fromPro(RolePro dto);
}
