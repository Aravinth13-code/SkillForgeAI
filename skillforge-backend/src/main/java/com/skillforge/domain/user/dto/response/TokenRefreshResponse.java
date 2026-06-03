package com.skillforge.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRefreshResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public TokenRefreshResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}