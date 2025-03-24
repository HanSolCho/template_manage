package com.onj.template_manage.DTO.Request.template;

import com.onj.template_manage.DTO.Response.Item.SelectedItemOptionResponseDTO;
import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Schema(description = "선택 템플릿의 아이템 정보 응답 DTO")
public class TemplateItemDTO {
    @Schema(description = "아이템 ID", example = "1")
    Long itemId;
    @Schema(description = "아이템 명", example = "1")
    String name;
    @Schema(description = "아이템 타입, TEXT,CHECKBOX,DROPDOWN 세가지 고정 타입", example = "CHECKBOX")
    ItemType type;
    @Schema(description = "아이템 등록 유저", example = "provider1")
    String provider;
    @Schema(description = "아이템 등록일", example = "2025-03-20T15:00:00.000+00:00")
    Date date;
    @Schema(description = "아이템 삭제여부", example = "false")
    Boolean isDeleted;
    @Schema(description = "선택 아이템 옵션 값 DTO")
    private List<SelectedItemOptionResponseDTO> selectedItemOptionResponseDTOList;

    public TemplateItemDTO(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.type = item.getType();
        this.provider = item.getProvider();
        this.isDeleted = item.getIsDeleted();
        this.date = item.getDate();
        this.selectedItemOptionResponseDTOList = item.getItemOptions().stream().map(SelectedItemOptionResponseDTO::new).collect(Collectors.toList());

    }
}
