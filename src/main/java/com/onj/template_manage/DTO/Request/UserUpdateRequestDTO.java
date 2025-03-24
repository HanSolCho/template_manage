package com.onj.template_manage.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원 정보 변경 요청 DTO")
public class UserUpdateRequestDTO {
    @Schema(description = "회원 ID", example = "userId")
    String id;
    @Schema(description = "기존 비밀번호", example = "originPassword")
    String presentPassword;
    @Schema(description = "변경할 비밀번호", example = "updatePassword")
    String updatePassword;
}
