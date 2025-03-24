package com.onj.template_manage.DTO.Response;

import com.onj.template_manage.entity.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "컨텐츠의 템플릿의 아이템 응답 DTO")
public class ContentSelectItemResponseDTO {
    @Schema(description = "아이템 ID", example = "1")
    Long id;
    @Schema(description = "아이템 명", example = "item1")
    String name;
    @Schema(description = "아이템 타입, TEXT,CHECKBOX,DROPDOWN 세가지 고정 타입", example = "DROPDOWN")
    ItemType type;
    @Schema(description = "아이템 등록 유저", example = "provider1")
    String provider;
    @Schema(description = "컨텐츠에 속한 템플릿의 아이템의 옵션")
    List<ContentSelectItemOptionResponseDTO> itemOptions;
}
