package com.onj.template_manage.controller;


import com.onj.template_manage.DTO.Request.ContentRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.service.ContentService;
import com.onj.template_manage.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/onj/template-manage/content")
public class ContentController {

    private ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerContent(@RequestBody ContentRegisterRequestDTO contentRegisterRequestDTO) {
        contentService.registerContent(contentRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }
}
