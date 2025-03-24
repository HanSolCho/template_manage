package com.onj.template_manage.DTO.Request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원 정보 요청 DTO")
public class UserSignUpRequestDTO {
    @Schema(description = "회원 ID", example = "userId")
    String id;
    @Schema(description = "회원 비밀번호", example = "userPassword")
    String password;
}
