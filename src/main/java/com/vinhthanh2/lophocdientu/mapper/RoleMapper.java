package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.req.RoleReq;
import com.vinhthanh2.lophocdientu.dto.res.RoleRes;
import com.vinhthanh2.lophocdientu.dto.sql.RolePro;
import com.vinhthanh2.lophocdientu.entity.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring"
)
public interface RoleMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Role fromDto(RoleReq roleReq);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RoleRes toDto(Role role);

    Role fromPro(RolePro dto);
}
