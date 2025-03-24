package com.onj.template_manage.DTO.Response.content;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "컨텐츠의 템플릿 응답 DTO")
public class ContentSelectTemplateResponseDTO {
    @Schema(description = "템플릿 ID", example = "1")
    Long id;
    @Schema(description = "템플릿 명", example = "template1")
    String name;
    @Schema(description = "템플릿 타입", example = "typeA")
    String type;
    @Schema(description = "템플릿 등록 유저", example = "provider1")
    String provider;
    @Schema(description = "컨텐츠에 속한 템플릿의 아이템")
    List<ContentSelectItemResponseDTO> templateItem;
}
