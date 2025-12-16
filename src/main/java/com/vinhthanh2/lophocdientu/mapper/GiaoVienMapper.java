package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.TeacherRes;
import com.vinhthanh2.lophocdientu.dto.sql.GiaoVienPro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                XaMapper.class,
                TinhMapper.class,
                LopMapper.class,
                TruongMapper.class
        }
)
public interface GiaoVienMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "xaId", target = "xa.id")
    @Mapping(source = "tenXa", target = "xa.ten")
    @Mapping(source = "tinhId", target = "xa.tinh.id")
    @Mapping(source = "tenTinh", target = "xa.tinh.ten")
    TeacherRes toGiaoVienRes(GiaoVienPro giaoVienPro);
}
