package com.onj.template_manage.DTO.Response.content;

import com.onj.template_manage.entity.ContentItemData;
import com.onj.template_manage.entity.ItemOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "컨텐츠의 템플릿의 아이템의 옵션 응답 DTO")
public class ContentSelectItemOptionResponseDTO {
//    @Schema(description = "아이템 옵션 ID", example = "1")
//    Long id;
    @Schema(description = "아이템 옵션 값", example = "DROPDOWN Option1")
    String optionValue;

    // 생성자
    public ContentSelectItemOptionResponseDTO(ContentItemData contentItemData) {
//        this.id = itemOption.getId();
        this.optionValue = contentItemData.getItemValue();
    }
}
