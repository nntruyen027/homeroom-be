package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.HocSinhRes;
import com.vinhthanh2.lophocdientu.dto.sql.HocSinhPro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {XaMapper.class, TinhMapper.class, LopMapper.class})
public interface HocSinhMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "xaId", target = "xa.id")
    @Mapping(source = "tenXa", target = "xa.ten")
    @Mapping(source = "tinhId", target = "xa.tinh.id")
    @Mapping(source = "tenTinh", target = "xa.tinh.ten")
    @Mapping(source = "lopId", target = "lop.id")
    @Mapping(source = "tenLop", target = "lop.ten")
    @Mapping(source = "truongId", target = "lop.truong.id")
    @Mapping(source = "tenTruong", target = "lop.truong.ten")
    HocSinhRes toHocSinhRes(HocSinhPro user);

}
