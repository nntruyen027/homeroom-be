package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.ThongBaoViewRes;
import com.vinhthanh2.lophocdientu.dto.sql.ThongBaoViewPro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThongBaoMapper {
    ThongBaoViewRes toRes(ThongBaoViewPro pro);
}
