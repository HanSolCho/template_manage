package com.onj.template_manage.DTO.Request;

import com.onj.template_manage.entity.ItemType;
import lombok.Data;

@Data
public class ItemSelectRequestDTO {
    //String name, ItemType type, String provider, int page, int size
    String name;
    ItemType type;
    String provider;
    int page;
    int size;
}
