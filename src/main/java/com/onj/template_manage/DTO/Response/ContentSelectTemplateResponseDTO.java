package com.onj.template_manage.DTO.Response;

import com.onj.template_manage.entity.AccessLevel;
import lombok.Data;

import java.util.List;

@Data
public class ContentSelectTemplateResponseDTO {
    Long id;
    String name;
    String type;
    String provider;
    List<ContentSelectItemResponseDTO> templateItem;
}
