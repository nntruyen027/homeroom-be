package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.TruongRes;
import com.vinhthanh2.lophocdientu.dto.sql.TruongPro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        XaMapper.class, TruongMapper.class
})
public interface TruongMapper {
    @Mapping(source = "xaId", target = "xa.id")
    @Mapping(source = "tenXa", target = "xa.ten")
    @Mapping(source = "tinhId", target = "xa.tinh.id")
    @Mapping(source = "tenTinh", target = "xa.tinh.ten")
    TruongRes toDto(TruongPro truong);
}
