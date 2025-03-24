package com.onj.template_manage.controller;

import com.onj.template_manage.DTO.Request.*;
import com.onj.template_manage.DTO.Response.SelectedItemResponsePagingDTO;
import com.onj.template_manage.DTO.Response.SelectedTemplateResponsePagingDTO;
import com.onj.template_manage.service.ItemService;
import com.onj.template_manage.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.experimental.Delegate;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onj/template-manage/template")
@Log4j2
@Tag(name = "템플릿 정보", description = "템플릿 정보에 대한 기능입니다.")
public class TemplateController {

    private TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping("/register")
    @Operation(summary = "템플릿 등록")
    public ResponseEntity<?> registerTemplate(@RequestBody TemplateRegisterRequestDTO templateRegisterRequestDTO) {
        log.error(templateRegisterRequestDTO);
        templateService.registerTemplate(templateRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/select")
    @Operation(summary = "템플릿 조회", responses = {
            @ApiResponse(responseCode = "200",
                    description = "컨텐츠 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SelectedTemplateResponsePagingDTO.class)))
    })// 수정
    public ResponseEntity<?> selectTemplate(@RequestBody TemplateSelectRequestDTO templateSelectRequestDTO) {
        return ResponseEntity.ok(templateService.selectTemplate(templateSelectRequestDTO));
    }

    @PutMapping("/update")
    @Operation(summary = "템플릿 정보 변경")
    public ResponseEntity<?> updateTemplate(@RequestBody TemplateRegisterRequestDTO templateRegisterRequestDTO) {
        templateService.updateTemplate(templateRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/delete")
    @Operation(summary = "템플릿 삭제, 소프트 삭제로 인해 DELETE가 아닌 PUT을 통한 DB 상태값 변경 방식")
    public ResponseEntity<?> deleteTemplate(@RequestBody TemplateDeleteRequsetDTO templateRegisterRequestDTO) {
        templateService.softDeleteTemplate(templateRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

}
