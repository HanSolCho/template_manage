package com.onj.template_manage.DTO.Request;

import com.onj.template_manage.entity.ItemType;
import lombok.Data;

import java.util.List;

@Data
public class ItemRegisterRequestDTO {
    String name;
    String provider;
    ItemType type;
    List<ItemOptionRegisterRequestDTO> option;
}
