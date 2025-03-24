package com.onj.template_manage.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
@Schema(description = "Json Web Token 응답 DTO")
public class JwtToken {
    @Schema(description = "Token 타입", example = "Bearer")
    private String grantType;
    @Schema(description = "accessToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMiIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE3NDI4NzkxODB9.mPBxlJ75eekfizGGCGk67cow9rgxNE6y07UJvVqypCU")
    private String accessToken;
    @Schema(description = "refreshToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3NDI4NzkxODB9.aQwYePzgYhBUop-CTluG7fMkPVYcC4WjXbAUpmM_aIQ")
    private String refreshToken;
}
