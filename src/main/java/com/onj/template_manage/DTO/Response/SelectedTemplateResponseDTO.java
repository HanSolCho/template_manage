package com.onj.template_manage.DTO.Response;

import com.onj.template_manage.DTO.Request.TemplateItemDTO;
import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemType;
import com.onj.template_manage.entity.Template;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SelectedTemplateResponseDTO {
    private Long id;
    private String name;
    private String type;
    private String provider;
    private Date date;
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
