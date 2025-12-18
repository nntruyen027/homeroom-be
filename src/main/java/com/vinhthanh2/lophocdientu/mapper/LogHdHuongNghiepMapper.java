package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.LogHdHuongNghiepRes;
import com.vinhthanh2.lophocdientu.dto.sql.LogHdHuongNghiepPro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LogHdHuongNghiepMapper {
    LogHdHuongNghiepRes toDto(LogHdHuongNghiepPro hdHuongNghiepPro);
}
