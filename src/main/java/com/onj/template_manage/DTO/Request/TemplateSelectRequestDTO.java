package com.onj.template_manage.DTO.Request;

import com.onj.template_manage.entity.AccessLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "템플릿 조회 DTO")
public class TemplateSelectRequestDTO {
    @Schema(description = "템플릿 접근 레벨, PUBLIC,PRIVATE 두가지 고정 레벨", example = "PUBLIC")
    AccessLevel accessLevel;
    @Schema(description = "템플릿 등록 유저", example = "provider1")
    String provider;
    @Schema(description = "템플릿 명", example = "template1")
    String name;
    @Schema(description = "템플릿 타입", example = "typeA")
    String type;
    @Schema(description = "템플릿의 페이지 Index", example = "0")
    int page;
    @Schema(description = "한번에 조회 할 템플릿의 개수", example = "10")
    int size;
}
