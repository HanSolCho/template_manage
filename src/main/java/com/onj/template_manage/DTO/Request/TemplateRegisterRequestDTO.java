package com.onj.template_manage.DTO.Request;


import com.onj.template_manage.entity.AccessLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "템플릿 정보 요청 DTO")
public class TemplateRegisterRequestDTO {
    @Schema(description = "템플릿 ID, 템플릿 등록시에는 빈값으로 전달되어 AUTO_INCREMENT로 등록", example = "1")
    Long id;
    @Schema(description = "템플릿 명", example = "template1")
    String name;
    @Schema(description = "템플릿 타입", example = "typeA")
    String type;
    @Schema(description = "템플릿 접근 레벨, PUBLIC,PRIVATE 두가지 고정 레벨", example = "PUBLIC")
    AccessLevel accessLevel;
    @Schema(description = "템플릿 등록 유저", example = "provider1")
    String provider;
    @Schema(description = "템플릿에 사용되는 Item id 목록", example = "\"item\": [\n" +
            "        {\n" +
            "            \"itemId\": 1\n" +
            "        },\n" +
            "        {\n" +
            "            \"itemId\": 2\n" +
            "        }\n" +
            "    ]")
    List<TemplateItemRegisterRequestDTO> item;
}
