package com.onj.template_manage.DTO.Request;

import com.onj.template_manage.entity.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "아이템 정보 요청 DTO")
public class ItemRegisterRequestDTO {
    @Schema(description = "아이템 ID, 아이템 등록시에는 빈값으로 전달되어 AUTO_INCREMENT로 등록", example = "1")
    Long id;
    @Schema(description = "아이템 명", example = "item1")
    String name;
    @Schema(description = "아이템 등록 유저", example = "provider1")
    String provider;
    @Schema(description = "아이템 타입, TEXT,CHECKBOX,DROPDOWN 세가지 고정 타입", example = "CHECKBOX")
    ItemType type;
    @Schema(description = "아이템 옵션 값, CHECKBOX,DROPDOWN 두가지 타입의 아이템의 선택 옵션 값"
            , example = "[\n" +
            "        {\n" +
            "            \"name\": \"Option1\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"Option2\"\n" +
            "        }]")
    List<ItemOptionRegisterRequestDTO> option;
}
