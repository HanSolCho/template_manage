package com.onj.template_manage.DTO.Response;

import com.onj.template_manage.entity.ItemType;
import lombok.Data;

import java.util.List;

@Data
public class ContentSelectItemResponseDTO {
    Long id;
    String name;
    ItemType type;
    String provider;
    List<ContentSelectItemOptionResponseDTO> itemOptions;
}
