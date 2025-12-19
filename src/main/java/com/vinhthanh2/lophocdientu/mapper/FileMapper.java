package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.FileRes;
import com.vinhthanh2.lophocdientu.dto.sql.FilePro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring"
)
public interface FileMapper {
    @Mapping(
            target = "updatedAt",
            source = "updatedAt",
            qualifiedByName = "timestampToLocalDateTime"
    )
    FileRes toDto(FilePro file);

    @Named("timestampToLocalDateTime")
    default LocalDateTime timestampToLocalDateTime(Timestamp ts) {
        return ts == null ? null : ts.toLocalDateTime();
    }
}
