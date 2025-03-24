package com.onj.template_manage.DTO.Request.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "아이템 옵션 등록 DTO")
public class ItemOptionRegisterRequestDTO {
    @Schema(description = "CHECKBOX,DROPDOWN 타입 아이템의 선택 옵션값", example = "option1")
    String name;
}
