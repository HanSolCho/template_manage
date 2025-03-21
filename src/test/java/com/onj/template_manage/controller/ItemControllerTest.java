package com.onj.template_manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemSelectRequestDTO;
import com.onj.template_manage.config.TestSecurityConfig;
import com.onj.template_manage.entity.ItemType;
import com.onj.template_manage.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ItemService itemService;


    @Test
    void registerItem() throws Exception {
        //given
        ItemRegisterRequestDTO itemRegisterRequestDTO = new ItemRegisterRequestDTO();
        itemRegisterRequestDTO.setName("item1");
        itemRegisterRequestDTO.setType(ItemType.TEXT);
        itemRegisterRequestDTO.setProvider("provider1");
        itemRegisterRequestDTO.setOption(new ArrayList<>());

        //when & then: 예상되는 결과 검증
        mockMvc.perform(post("/onj/template-manage/item/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemRegisterRequestDTO)))
                .andExpect(status().isOk());

        verify(itemService, times(1)).registerItem(itemRegisterRequestDTO);
    }

    @Test
    void selectItems() throws Exception {
        //given
        ItemSelectRequestDTO itemSelectRequestDTO = new ItemSelectRequestDTO();
        itemSelectRequestDTO.setName("item1");
        itemSelectRequestDTO.setType(ItemType.TEXT);
        itemSelectRequestDTO.setProvider("provider1");
        itemSelectRequestDTO.setPage(0);
        itemSelectRequestDTO.setSize(3);

        //when & then: 예상되는 결과 검증
        mockMvc.perform(post("/onj/template-manage/item/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemSelectRequestDTO)))
                .andExpect(status().isOk());

        verify(itemService, times(1)).selectItems(itemSelectRequestDTO);

    }
}