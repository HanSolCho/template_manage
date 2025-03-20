package com.onj.template_manage.DTO.Request;

import com.onj.template_manage.entity.ItemType;
import lombok.Data;

import java.util.List;

@Data
public class ItemRegisterRequestDTO {
    String name;
    String provider;
    ItemType type;
    List<String> option;
    //옵션리스트추가로 담아야함.

}
