package com.onj.template_manage.DTO.Response;

import com.onj.template_manage.DTO.Request.TemplateItemDTO;
import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemType;
import com.onj.template_manage.entity.Template;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Schema(description = "선택 템플릿 정보 응답 DTO")
public class SelectedTemplateResponseDTO {
    @Schema(description = "템플릿 ID", example = "1")
    private Long id;
    @Schema(description = "템플릿 명", example = "template1")
    private String name;
    @Schema(description = "템플릿 타입", example = "typeA")
    private String type;
    @Schema(description = "템플릿 등록 유저", example = "provider1")
    private String provider;
    @Schema(description = "템플릿 등록일", example = "2025-03-20T15:00:00.000+00:00")
    private Date date;
    @Schema(description = "템플릿에 사용된 아이템")
    private List<TemplateItemDTO> templateItem;

    public SelectedTemplateResponseDTO(Template template){
        this.id = template.getId();
        this.name = template.getName();
        this.type = template.getType();
        this.date = template.getDate();
        this.provider = template.getProvider();
        this.templateItem = (template.getTemplateItem() == null)
                ? new ArrayList<>()
                : template.getTemplateItem().stream()
                .map(TemplateItemDTO::new)
                .collect(Collectors.toList());
    }
}
