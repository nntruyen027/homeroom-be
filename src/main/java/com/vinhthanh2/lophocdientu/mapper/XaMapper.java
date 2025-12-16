package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.XaRes;
import com.vinhthanh2.lophocdientu.dto.sql.XaPro;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {XaMapper.class}
)
public interface XaMapper {
    @Mapping(source = "tenTinh", target = "tinh.ten")
    @Mapping(source = "tinhId", target = "tinh.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    XaRes toDto(XaPro xa);


}
