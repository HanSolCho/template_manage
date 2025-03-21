package com.onj.template_manage.DTO.Request;

import com.onj.template_manage.entity.AccessLevel;
import lombok.Data;

@Data
public class TemplateSelectRequestDTO {
    AccessLevel accessLevel;
    String provider;
    String name;
    String type;
    int page;
    int size;
}
