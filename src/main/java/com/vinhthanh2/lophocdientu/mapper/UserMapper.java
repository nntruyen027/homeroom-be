package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.UserFullRes;
import com.vinhthanh2.lophocdientu.dto.sql.UserFullPro;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    UserFullRes toUserFullRes(UserFullPro userFullPro);

    @AfterMapping
    default void afterToUserFullRes(
            UserFullPro pro,
            @MappingTarget UserFullRes res
    ) {
        // roles
        if (pro.getRoles() != null) {
            res.setRoles(Arrays.asList(pro.getRoles()));
        } else {
            res.setRoles(List.of());
        }

        // permissions
        if (pro.getPermissions() != null) {
            res.setPermissions(Arrays.asList(pro.getPermissions()));
        } else {
            res.setPermissions(List.of());
        }
    }
}
