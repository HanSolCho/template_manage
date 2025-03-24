package com.onj.template_manage.controller;

import com.onj.template_manage.DTO.Request.ItemDeleteRequestDTO;
import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemSelectRequestDTO;
import com.onj.template_manage.DTO.Response.ContentSelectResponseDTO;
import com.onj.template_manage.DTO.Response.SelectedItemResponsePagingDTO;
import com.onj.template_manage.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onj/template-manage/item")
@Tag(name = "아이템 정보", description = "아이템 정보에 대한 기능입니다.")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/register")
    @Operation(summary = "아이템 등록")
    public ResponseEntity<?> registerItem(@RequestBody ItemRegisterRequestDTO itemRegisterRequestDTO) {
        itemService.registerItem(itemRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/select")
    @Operation(summary = "아이템 조회", responses = {
            @ApiResponse(responseCode = "200",
                    description = "컨텐츠 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SelectedItemResponsePagingDTO.class)))
    })
    public ResponseEntity<?> selectItems(@RequestBody ItemSelectRequestDTO itemSelectRequestDTO) {
        return ResponseEntity.ok(itemService.selectItems(itemSelectRequestDTO));
    }

    @PutMapping("/update")
    @Operation(summary = "아이템 정보 변경")
    public ResponseEntity<?> updateItems(@RequestBody ItemRegisterRequestDTO itemRegisterRequestDTO) {
        itemService.updateItem(itemRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    //소프트 delete 방식으로 db를 통한 상태값 관리 형식이기에 put채택
    @PutMapping("/delete")
    @Operation(summary = "아이템 삭제, 소프트 삭제로 인해 DELETE가 아닌 PUT을 통한 DB 상태값 변경 방식")
    public ResponseEntity<?> deleteItems(@RequestBody ItemDeleteRequestDTO itemDeleteRequestDTO) {
        itemService.softDeleteItem(itemDeleteRequestDTO);
        return ResponseEntity.ok().build();
    }
}
