package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.HdHuongNghiepRes;
import com.vinhthanh2.lophocdientu.dto.sql.HdHuongNghiepPro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HdHuongNghiepMapper {
    @Mapping(source = "id", target = "id")
    HdHuongNghiepRes toDto(HdHuongNghiepPro hdHuongNghiepPro);
}
