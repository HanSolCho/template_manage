package com.onj.template_manage.DTO.Response;

import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Schema(description = "선택 아이템 정보 응답 DTO")
public class SelectedItemResponseDTO {
    @Schema(description = "아이템 ID", example = "1")
    private Long id;
    @Schema(description = "아이템 명", example = "item1")
    private String name;
    @Schema(description = "아이템 타입, TEXT,CHECKBOX,DROPDOWN 세가지 고정 타입", example = "CHECKBOX")
    private ItemType type;
    @Schema(description = "아이템 등록 유저", example = "provider1")
    private String provider;
    @Schema(description = "아이템 등록 일", example = "2025-03-20T15:00:00.000+00:00")
    private Date date;
    @Schema(description = "선택 아이템 옵션 값 DTO")
    private List<SelectedItemOptionResponseDTO> selectedItemOptionResponseDTOList;

    public SelectedItemResponseDTO(Item item){
        this.id = item.getId();
        this.name = item.getName();
        this.type = item.getType();
        this.date = item.getDate();
        this.provider = item.getProvider();
        this.selectedItemOptionResponseDTOList = item.getItemOptions().stream().map(SelectedItemOptionResponseDTO::new).collect(Collectors.toList());
    }
}
