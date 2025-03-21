package com.onj.template_manage.DTO.Response;

import lombok.Data;

import java.util.List;

@Data
public class SelectedTemplateResponsePagingDTO {

    private List<SelectedTemplateResponseDTO> templateS;
    private int number;
    private int size;

    public SelectedTemplateResponsePagingDTO(List<SelectedTemplateResponseDTO> templateS, int number, int size) {
        this.templateS = templateS;
        this.number = number;
        this.size = size;
    }
}
