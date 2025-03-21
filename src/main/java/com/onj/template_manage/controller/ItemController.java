package com.onj.template_manage.controller;

import com.onj.template_manage.DTO.Request.ItemDeleteRequestDTO;
import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemSelectRequestDTO;
import com.onj.template_manage.DTO.Request.UserSignUpRequestDTO;
import com.onj.template_manage.DTO.Response.SelectedItemResponsePagingDTO;
import com.onj.template_manage.jwt.JwtToken;
import com.onj.template_manage.service.ItemService;
import com.onj.template_manage.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onj/template-manage/item")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerItem(@RequestBody ItemRegisterRequestDTO itemRegisterRequestDTO) {
        itemService.registerItem(itemRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/select")
    public ResponseEntity<?> selectItems(@RequestBody ItemSelectRequestDTO itemSelectRequestDTO) {
        return ResponseEntity.ok(itemService.selectItems(itemSelectRequestDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateItems(@RequestBody ItemRegisterRequestDTO itemRegisterRequestDTO) {
        itemService.updateItem(itemRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    //소프트 delete 방식으로 db를 통한 상태값 관리 형식이기에 put채택
    @PutMapping("/delete")
    public ResponseEntity<?> deleteItems(@RequestBody ItemDeleteRequestDTO itemDeleteRequestDTO) {
        itemService.softDeleteItem(itemDeleteRequestDTO);
        return ResponseEntity.ok().build();
    }
}
