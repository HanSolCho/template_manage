package com.onj.template_manage.DTO.Request;

import com.onj.template_manage.entity.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "아이템 조회 DTO")
public class ItemSelectRequestDTO {
    @Schema(description = "아이템 명", example = "item1")
    String name;
    @Schema(description = "아이템 타입, TEXT,CHECKBOX,DROPDOWN 세가지 고정 타입", example = "TEXT")
    ItemType type;
    @Schema(description = "아이템 등록 유저", example = "provider1")
    String provider;
    @Schema(description = "아이템의 페이지 Index", example = "0")
    int page;
    @Schema(description = "한번에 조회 할 아이템의 개수", example = "10")
    int size;
}
