package com.vinhthanh2.lophocdientu.config;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses({
        @ApiResponse(responseCode = "401", description = "Không có hoặc token không hợp lệ", content = @Content),
        @ApiResponse(responseCode = "403", description = "Không có quyền truy cập", content = @Content)
})
public @interface SecurityApiResponses {
}
