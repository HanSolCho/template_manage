package com.onj.template_manage.controller;

import com.onj.template_manage.DTO.Request.*;
import com.onj.template_manage.service.ItemService;
import com.onj.template_manage.service.TemplateService;
import lombok.experimental.Delegate;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onj/template-manage/template")
@Log4j2
public class TemplateController {

    private TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTemplate(@RequestBody TemplateRegisterRequestDTO templateRegisterRequestDTO) {
        log.error(templateRegisterRequestDTO);
        templateService.registerTemplate(templateRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/select") //결과값에 Item의 id가 담기는데 그걸 토대로 Item의 정보를 추출해올수있도록 수정
    public ResponseEntity<?> selectTemplate(@RequestBody TemplateSelectRequestDTO templateSelectRequestDTO) {
        return ResponseEntity.ok(templateService.selectTemplate(templateSelectRequestDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTemplate(@RequestBody TemplateRegisterRequestDTO templateRegisterRequestDTO) {
        templateService.updateTemplate(templateRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTemplate(@RequestBody TemplateDeleteRequsetDTO templateRegisterRequestDTO) {
        templateService.softDeleteTemplate(templateRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

}
