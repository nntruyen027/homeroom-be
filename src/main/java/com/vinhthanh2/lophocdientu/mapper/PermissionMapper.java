package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.req.PermissionReq;
import com.vinhthanh2.lophocdientu.dto.res.PermissionRes;
import com.vinhthanh2.lophocdientu.dto.sql.PermissionPro;
import com.vinhthanh2.lophocdientu.entity.Permission;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring"
)
public interface PermissionMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Permission fromDto(PermissionReq permissionReq);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PermissionRes toDto(Permission permission);

    Permission fromPro(PermissionPro dto);

}
