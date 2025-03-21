package com.onj.template_manage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemSelectRequestDTO;
import com.onj.template_manage.DTO.Request.TemplateRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.TemplateSelectRequestDTO;
import com.onj.template_manage.config.TestSecurityConfig;
import com.onj.template_manage.entity.AccessLevel;
import com.onj.template_manage.entity.ItemType;
import com.onj.template_manage.service.ItemService;
import com.onj.template_manage.service.TemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TemplateController.class)
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class TemplateControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TemplateService templateService;

    @Test
    void registerTemplate() throws Exception {
        //given
        TemplateRegisterRequestDTO templateRegisterRequestDTO = new TemplateRegisterRequestDTO();
        templateRegisterRequestDTO.setName("template1");
        templateRegisterRequestDTO.setType("type1");
        templateRegisterRequestDTO.setProvider("provider1");
        templateRegisterRequestDTO.setAccessLevel(AccessLevel.PUBLIC);
        templateRegisterRequestDTO.setItem(new ArrayList<>());

        //when & then: 예상되는 결과 검증
        mockMvc.perform(post("/onj/template-manage/template/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(templateRegisterRequestDTO)))
                .andExpect(status().isOk());

        verify(templateService, times(1)).registerTemplate(templateRegisterRequestDTO);
    }

    @Test
    void selectTemplate() throws Exception {

        //given
        TemplateSelectRequestDTO templateSelectRequestDTO = new TemplateSelectRequestDTO();
        templateSelectRequestDTO.setName("template1");
        templateSelectRequestDTO.setType("type1");
        templateSelectRequestDTO.setProvider("provider1");
        templateSelectRequestDTO.setAccessLevel(AccessLevel.PUBLIC);
        templateSelectRequestDTO.setPage(0);
        templateSelectRequestDTO.setSize(3);

        //when & then: 예상되는 결과 검증
        mockMvc.perform(post("/onj/template-manage/template/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(templateSelectRequestDTO)))
                .andExpect(status().isOk());

        verify(templateService, times(1)).selectTemplate(templateSelectRequestDTO);
    }
}