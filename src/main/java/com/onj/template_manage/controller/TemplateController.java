package com.onj.template_manage.controller;

import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemSelectRequestDTO;
import com.onj.template_manage.DTO.Request.TemplateRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.TemplateSelectRequestDTO;
import com.onj.template_manage.service.ItemService;
import com.onj.template_manage.service.TemplateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
