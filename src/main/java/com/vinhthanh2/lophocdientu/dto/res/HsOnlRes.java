package com.vinhthanh2.lophocdientu.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class HsOnlRes {
    private Long userId;
    private LocalDateTime lastSeen;
}
