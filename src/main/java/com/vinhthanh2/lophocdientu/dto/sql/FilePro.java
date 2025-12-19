package com.vinhthanh2.lophocdientu.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class FilePro {
    private Long id;
    private String fileName;
    private String storedName;
    private String url;
    private String contentType;
    private Long size;
    private Long userId;
    private Timestamp updatedAt;
}
