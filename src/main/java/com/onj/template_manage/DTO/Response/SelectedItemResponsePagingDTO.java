package com.onj.template_manage.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "아이템 조회 응답 DTO")
public class SelectedItemResponsePagingDTO {
    @Schema(description = "아이템 정보 DTO")
    private List<SelectedItemResponseDTO> items;
    @Schema(description = "페이지 번호", example = "0")
    private int number;
    @Schema(description = "페이지 크기(한 페이지당 항목 수)", example = "10")
    private int size;

    public SelectedItemResponsePagingDTO(List<SelectedItemResponseDTO> items, int number, int size) {
        this.items = items;
        this.number = number;
        this.size = size;
    }
}
