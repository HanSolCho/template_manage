package com.onj.template_manage.DTO.Request;


import com.onj.template_manage.entity.AccessLevel;
import lombok.Data;

import java.util.List;

@Data
public class TemplateRegisterRequestDTO {
    Long id;
    String name;
    String type;
    AccessLevel accessLevel;
    String provider;
    List<TemplateItemRegisterRequestDTO> item;
}
