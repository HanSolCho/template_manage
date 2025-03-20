package com.onj.template_manage.DTO.Response;

import com.onj.template_manage.entity.ItemOption;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SelectedItemOptionResponseDTO {
    private Long id;
    private String optionValue;

    // 생성자
    public SelectedItemOptionResponseDTO(ItemOption option) {
        this.id = option.getId();
        this.optionValue = option.getOptionValue();
    }
}
