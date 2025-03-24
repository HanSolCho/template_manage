package com.onj.template_manage.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "컨텐츠 리스트 조회 응답 DTO")
public class ContentListResponseDTO {
    @Schema(description = "컨텐츠 ID", example = "1")
    Long id;
    @Schema(description = "컨텐츠 명", example = "content1")
    String name;
}
