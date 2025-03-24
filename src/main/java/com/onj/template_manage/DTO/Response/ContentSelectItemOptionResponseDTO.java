package com.onj.template_manage.DTO.Response;

import com.onj.template_manage.entity.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "컨텐츠의 템플릿의 아이템의 옵션 응답 DTO")
public class ContentSelectItemOptionResponseDTO {
    @Schema(description = "아이템 옵션 ID", example = "1")
    Long id;
    @Schema(description = "아이템 옵션 값", example = "DROPDOWN Option1")
    String optionValue;
}
