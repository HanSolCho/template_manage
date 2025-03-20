package com.onj.template_manage.DTO.Response;

import lombok.Data;

import java.util.List;

@Data
public class SelectedItemResponsePagingDTO {
    private List<SelectedItemResponseDTO> items;
    private int number;
    private int size;

    public SelectedItemResponsePagingDTO(List<SelectedItemResponseDTO> items, int number, int size) {
        this.items = items;
        this.number = number;
        this.size = size;
    }
}
