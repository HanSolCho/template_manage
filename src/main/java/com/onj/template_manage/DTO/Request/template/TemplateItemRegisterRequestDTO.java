package com.onj.template_manage.DTO.Request.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "템플릿 사용 아이템 DTO")
public class TemplateItemRegisterRequestDTO {
    @Schema(description = "아이템 ID", example = "1")
    Long itemId;
}
