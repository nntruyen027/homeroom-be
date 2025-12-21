package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.HsOnlRes;
import com.vinhthanh2.lophocdientu.dto.sql.HsOnlPro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PresenceMapper {
    @Mapping(
            target = "lastSeen",
            source = "lastSeen",
            qualifiedByName = "timestampToLocalDateTime"
    )
    HsOnlRes toRes(HsOnlPro pro);

    @Named("timestampToLocalDateTime")
    default LocalDateTime timestampToLocalDateTime(Timestamp ts) {
        return ts == null ? null : ts.toLocalDateTime();
    }
}
