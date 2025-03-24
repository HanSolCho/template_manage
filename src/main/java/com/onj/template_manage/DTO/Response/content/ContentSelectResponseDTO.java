package com.onj.template_manage.DTO.Response.content;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "컨텐츠 조회 응답 DTO")
public class ContentSelectResponseDTO {
    @Schema(description = "컨텐츠 ID", example = "1")
    Long id;
    @Schema(description = "컨텐츠 명", example = "content1")
    String name;
    @Schema(description = "컨텐츠 포함 템플릿 DTO")
    ContentSelectTemplateResponseDTO template;
    // 생성자
}
