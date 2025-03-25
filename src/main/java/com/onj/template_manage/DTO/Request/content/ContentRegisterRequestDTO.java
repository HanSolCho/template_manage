package com.onj.template_manage.DTO.Request.content;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "컨텐츠 정보 요청 DTO")
public class ContentRegisterRequestDTO {
    @Schema(description = "컨텐츠 ID, 컨텐츠 등록시에는 빈값으로 전달되어 AUTO_INCREMENT로 등록", example = "1")
    Long id;
    @Schema(description = "컨텐츠 명", example = "content1")
    String name;
    @Schema(description = "컨텐츠 등록 유저", example = "provider1")
    String provider;
    @Schema(description = "선택 템플릿 ID", example = "1")
    Long templateId;
    @Schema(description = "선택 아이템 값, 템플릿에 속한 아이템의 값으로 TEXT 타입의 경우 입력값, 그외 타입의 경우 등록된 옵션 값중 선택 값"
            , example = "[\n" +
            "    {\n" +
            "      \"itemId\": 1,\n" +
            "      \"value\": \"text typing\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"itemId\": 2,\n" +
            "      \"value\": \"option a\"\n" +
            "    }\n" +
            "    ]")
    List<ContentItemDataRegisterRequestDTO> itemDataList;
}
