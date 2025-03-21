package com.onj.template_manage.DTO.Request;

import com.onj.template_manage.entity.ItemType;
import lombok.Data;

import java.util.List;

@Data
public class ItemDeleteRequestDTO {
    Long id;
    String provider;
}