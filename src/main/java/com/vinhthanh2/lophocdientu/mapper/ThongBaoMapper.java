package com.vinhthanh2.lophocdientu.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinhthanh2.lophocdientu.dto.res.ThongBaoViewRes;
import com.vinhthanh2.lophocdientu.dto.res.UserDaXemDto;
import com.vinhthanh2.lophocdientu.dto.sql.ThongBaoViewPro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ThongBaoMapper {
    @Mapping(
            target = "dsUserDaXem",
            source = "dsUserDaXem",
            qualifiedByName = "jsonToUserList"
    )
    @Mapping(
            target = "thoiGianTao",
            source = "thoiGianTao",
            qualifiedByName = "timestampToLocalDateTime"
    )
    ThongBaoViewRes toRes(ThongBaoViewPro pro);

    @Named("jsonToUserList")
    default List<UserDaXemDto> jsonToUserList(String json) {
        if (json == null || json.isBlank()) {
            return List.of();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(
                    json,
                    new TypeReference<List<UserDaXemDto>>() {
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException("Không parse được dsUserDaXem JSON", e);
        }
    }

    @Named("timestampToLocalDateTime")
    default LocalDateTime timestampToLocalDateTime(Timestamp ts) {
        return ts == null ? null : ts.toLocalDateTime();
    }
}
