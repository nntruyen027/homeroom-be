package com.vinhthanh2.lophocdientu.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class FileReq {
    private String fileName;
    private String storedName;
    private String url;
    private String contentType;
    private Long size;
    private Long userId;
    private LocalDateTime uploadedAt;
}
