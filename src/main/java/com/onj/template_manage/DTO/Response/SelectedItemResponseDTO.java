package com.onj.template_manage.DTO.Response;

import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemType;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SelectedItemResponseDTO {
    private Long id;
    private String name;
    private ItemType type;
    private String provider;
    private Date date;
    private List<SelectedItemOptionResponseDTO> selectedItemOptionResponseDTOList;

    public SelectedItemResponseDTO(Item item){
        this.id = item.getId();
        this.name = item.getName();
        this.type = item.getType();
        this.date = item.getDate();
        this.provider = item.getProvider();
        this.selectedItemOptionResponseDTOList = item.getItemOptions().stream().map(SelectedItemOptionResponseDTO::new).collect(Collectors.toList());
    }
}
