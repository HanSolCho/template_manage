package com.onj.template_manage.DTO.Request;

import com.onj.template_manage.entity.ItemType;
import lombok.Data;

@Data
public class ItemDTO {
    private Long id;
    private String name;
    private ItemType type;
}
