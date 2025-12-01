package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.req.LopReq;
import com.vinhthanh2.lophocdientu.dto.res.LopRes;
import com.vinhthanh2.lophocdientu.dto.sql.LopPro;
import com.vinhthanh2.lophocdientu.entity.Lop;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {TruongMapper.class, UserMapper.class}
)
public interface LopMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Lop fromDto(LopReq lopReq);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    LopRes toDto(Lop lop);

    @Mapping(source = "outId", target = "id")
    @Mapping(source = "hinhAnh", target = "hinhAnh")
    @Mapping(source = "truongId", target = "truong.id")
    @Mapping(source = "giaoVienId", target = "giaoVien.id")
    @Mapping(source = "tenGiaoVien", target = "giaoVien.hoTen")
    @Mapping(source = "tenTruong", target = "truong.ten")
    Lop fromPro(LopPro dto);

}
