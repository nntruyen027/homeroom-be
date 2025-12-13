package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.StudentRes;
import com.vinhthanh2.lophocdientu.dto.sql.HocSinhPro;
import com.vinhthanh2.lophocdientu.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {XaMapper.class, TinhMapper.class, LopMapper.class})
public interface HocSinhMapper {
    
    StudentRes toStudentDto(User user);

    @Mapping(source = "outId", target = "id")
    @Mapping(source = "xaId", target = "xa.id")
    @Mapping(source = "tenXa", target = "xa.ten")
    @Mapping(source = "tenTinh", target = "xa.tinh.ten")
    @Mapping(source = "tinhId", target = "xa.tinh.id")
    @Mapping(source = "lopId", target = "lop.id")
    @Mapping(source = "tenLop", target = "lop.ten")
    @Mapping(source = "truongId", target = "lop.truong.id")
    @Mapping(source = "tenTruong", target = "lop.truong.ten")
    User fromHocSinhPro(HocSinhPro dto);


}
