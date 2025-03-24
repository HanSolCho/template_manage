package com.onj.template_manage.controller;


import com.onj.template_manage.DTO.Request.content.ContentDeleteRequestDTO;
import com.onj.template_manage.DTO.Request.content.ContentRegisterRequestDTO;
import com.onj.template_manage.DTO.Response.content.ContentListResponseDTO;
import com.onj.template_manage.DTO.Response.content.ContentSelectResponseDTO;
import com.onj.template_manage.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onj/template-manage/content")
@Tag(name = "컨텐츠 정보", description = "컨텐츠 정보에 대한 기능입니다.")
public class ContentController {

    private ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }


    @PostMapping("/register")
    @Operation(summary = "컨텐츠 등록")
    public ResponseEntity<?> registerContent(@RequestBody ContentRegisterRequestDTO contentRegisterRequestDTO) {
        contentService.registerContent(contentRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/select/all")
    @Operation(summary = "전체 컨텐츠 리스트 조회", responses = {
            @ApiResponse(responseCode = "200",
                    description = "컨텐츠 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContentListResponseDTO.class)))
    })
    public ResponseEntity<?> selectContent(@Parameter(description = "페이지 번호", example = "0")
                                           @RequestParam(value = "pageIndex", defaultValue = "0") int page,
                                           @Parameter(description = "페이지 크기(한 페이지당 항목 수)", example = "10")
                                           @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        return ResponseEntity.ok(contentService.selectContentList(page,size));
    }

    @GetMapping("/select")
    @Operation(summary = "컨텐츠 정보 조회", responses = {
            @ApiResponse(responseCode = "200",
                    description = "컨텐츠 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContentSelectResponseDTO.class)))
    })
    public ResponseEntity<?> selectContent(@Parameter(description = "컨텐츠 ID", example = "1")
                                           @RequestParam Long contentId) {
        return ResponseEntity.ok(contentService.selectContent(contentId));
    }

    @PutMapping("/update")
    @Operation(summary = "컨텐츠 정보 변경")
    public ResponseEntity<?> updateContent(@RequestBody ContentRegisterRequestDTO contentRegisterRequestDTO) {
        contentService.updateContent(contentRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "컨텐츠 삭제")
    public ResponseEntity<?> deleteContent(@RequestBody ContentDeleteRequestDTO contentDeleteRequestDTO) {
        contentService.deleteContent(contentDeleteRequestDTO);
        return ResponseEntity.ok().build();
    }
}
