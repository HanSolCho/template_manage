package com.onj.template_manage.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "컨텐츠 등록 시 선택 아이템 값 DTO")
public class ContentItemDataRegisterRequestDTO {
    @Schema(description = "아이템 ID", example = "1")
    Long itemId;
    @Schema(description = "아이템 값", example = "option1")
    String value;
}
