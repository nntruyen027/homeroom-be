package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.KetQuaHollandRes;
import com.vinhthanh2.lophocdientu.dto.res.ThongKeHollandTheoLopRes;
import com.vinhthanh2.lophocdientu.dto.sql.KetQuaHollandPro;
import com.vinhthanh2.lophocdientu.dto.sql.ThongKeHollandTheoLopPro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface HollandMapper {

    @Mapping(
            target = "ngayDanhGia",
            source = "ngayDanhGia",
            qualifiedByName = "timestampToLocalDateTime"
    )
    KetQuaHollandRes toRes(KetQuaHollandPro pro);

    ThongKeHollandTheoLopRes toRes(ThongKeHollandTheoLopPro pro);

    @Named("timestampToLocalDateTime")
    default LocalDateTime timestampToLocalDateTime(Timestamp ts) {
        return ts == null ? null : ts.toLocalDateTime();
    }

}
