package com.vinhthanh2.lophocdientu.mapper;

import com.vinhthanh2.lophocdientu.dto.res.FileRes;
import com.vinhthanh2.lophocdientu.dto.sql.FilePro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring"
)
public interface FileMapper {
    FileRes toDto(FilePro file);
}
