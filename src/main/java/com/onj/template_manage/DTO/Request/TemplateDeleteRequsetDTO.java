package com.onj.template_manage.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "템플릿 삭제 요청 DTO")
public class TemplateDeleteRequsetDTO {
    @Schema(description = "템플릿 ID", example = "1")
    Long id;
    @Schema(description = "현재 요청한 유저 ID", example = "user1")
    //해당 변수명이 사용 의미와 맞지 않음, 1차 개발 진행 후 변수 명 변경 후 테스트 필요
    String provider;
}
