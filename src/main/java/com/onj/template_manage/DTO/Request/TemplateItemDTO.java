package com.onj.template_manage.DTO.Request;

import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemType;
import lombok.Data;

import java.util.Date;

@Data
public class TemplateItemDTO {
    Long itemId;
    String name;
    ItemType type;
    String provider;
    Date date;
    Boolean isDeleted;


    public TemplateItemDTO(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.type = item.getType();
        this.provider = item.getProvider();
        this.isDeleted = item.getIsDeleted();
        this.date = item.getDate();
    }
}
