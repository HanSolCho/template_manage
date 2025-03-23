package com.onj.template_manage.controller;


import com.onj.template_manage.DTO.Request.ContentDeleteResponseDTO;
import com.onj.template_manage.DTO.Request.ContentRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.service.ContentService;
import com.onj.template_manage.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/select/all")
    public ResponseEntity<?> selectContent() {
        return ResponseEntity.ok(contentService.selectContentList());
    }

    @GetMapping("/select")
    public ResponseEntity<?> selectContent(@RequestParam Long contentId) {
        return ResponseEntity.ok(contentService.selectContent(contentId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateContent(@RequestBody ContentRegisterRequestDTO contentRegisterRequestDTO) {
        contentService.updateContent(contentRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteContent(@RequestBody ContentDeleteResponseDTO contentDeleteResponseDTO) {
        contentService.deleteContent(contentDeleteResponseDTO);
        return ResponseEntity.ok().build();
    }
}
