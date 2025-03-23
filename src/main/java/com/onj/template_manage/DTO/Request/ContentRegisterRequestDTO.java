package com.onj.template_manage.DTO.Request;

import com.onj.template_manage.entity.ContentItemData;
import com.onj.template_manage.entity.Template;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ContentRegisterRequestDTO {
    Long id;
    String name;
    String provider;
    Long templateId;
    List<ContentItemDataRegisterRequestDTO> itemDataList; // 이걸 DTO로 만들어서 담고
}
