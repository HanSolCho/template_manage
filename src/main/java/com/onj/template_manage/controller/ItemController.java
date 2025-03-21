package com.onj.template_manage.controller;

import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemSelectRequestDTO;
import com.onj.template_manage.DTO.Request.UserSignUpRequestDTO;
import com.onj.template_manage.DTO.Response.SelectedItemResponsePagingDTO;
import com.onj.template_manage.jwt.JwtToken;
import com.onj.template_manage.service.ItemService;
import com.onj.template_manage.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
