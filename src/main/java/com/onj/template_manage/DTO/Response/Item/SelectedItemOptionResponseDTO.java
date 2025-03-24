package com.onj.template_manage.DTO.Response.Item;

import com.onj.template_manage.entity.ItemOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "선택 아이템 옵션 값 응답 DTO")
public class SelectedItemOptionResponseDTO {
    @Schema(description = "아이템 옵션 ID", example = "1")
    private Long id;
    @Schema(description = "아이템 옵션 값", example = "CHECKBOX Option1")
    private String optionValue;

    // 생성자
    public SelectedItemOptionResponseDTO(ItemOption option) {
        this.id = option.getId();
        this.optionValue = option.getOptionValue();
    }
}
