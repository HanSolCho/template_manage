package com.onj.template_manage.DTO.Response;

import com.onj.template_manage.entity.ItemOption;
import com.onj.template_manage.entity.ItemType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ContentSelectResponseDTO {
    Long id;
    String name;
    ContentSelectTemplateResponseDTO template;

    // 생성자
}
