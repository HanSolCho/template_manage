package com.onj.template_manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onj.template_manage.DTO.Request.ContentDeleteRequestDTO;
import com.onj.template_manage.DTO.Request.ContentRegisterRequestDTO;
import com.onj.template_manage.config.TestSecurityConfig;
import com.onj.template_manage.service.ContentService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContentController.class)
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class ContentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ContentService contentService;

    @Test
    void registerContent() throws Exception {
        //given
        ContentRegisterRequestDTO contentRegisterRequestDTO = new ContentRegisterRequestDTO();
        contentRegisterRequestDTO.setName("content1");
        contentRegisterRequestDTO.setProvider("provider1");
        contentRegisterRequestDTO.setTemplateId(1L);
        contentRegisterRequestDTO.setItemDataList(new ArrayList<>());

        //when & then: 예상되는 결과 검증
        mockMvc.perform(post("/onj/template-manage/content/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(contentRegisterRequestDTO)))
                .andExpect(status().isOk());

        verify(contentService, times(1)).registerContent(contentRegisterRequestDTO);
    }

    @Test
    void selectContentList() throws Exception {
        //given
        int page = 1;
        int size = 10;

        //when & then: 예상되는 결과 검증
        mockMvc.perform(get("/onj/template-manage/content/select/all")
                        .param("pageIndex",String.valueOf(page))
                        .param("pageSize",String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(contentService, times(1)).selectContentList(page,size);
    }

    @Test
    void selectContent() throws Exception {
            //given
            Long contentId = 1L;

            //when & then: 예상되는 결과 검증
            mockMvc.perform(get("/onj/template-manage/content/select")
                            .param("contentId",String.valueOf(contentId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(contentService, times(1)).selectContent(contentId);
    }

    @Test
    void updateContent() throws Exception {
        //given
        ContentRegisterRequestDTO contentRegisterRequestDTO = new ContentRegisterRequestDTO();
        contentRegisterRequestDTO.setName("content1");
        contentRegisterRequestDTO.setProvider("provider1");
        contentRegisterRequestDTO.setTemplateId(1L);
        contentRegisterRequestDTO.setItemDataList(new ArrayList<>());

        //when & then: 예상되는 결과 검증
        mockMvc.perform(put("/onj/template-manage/content/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(contentRegisterRequestDTO)))
                .andExpect(status().isOk());

        verify(contentService, times(1)).updateContent(contentRegisterRequestDTO);
    }

    @Test
    void deleteContent() throws Exception {
        //given
        ContentDeleteRequestDTO contentDeleteRequestDTO =  new ContentDeleteRequestDTO();
        contentDeleteRequestDTO.setId(1L);
        contentDeleteRequestDTO.setProvider("provider1");

        //when & then: 예상되는 결과 검증
        mockMvc.perform(delete("/onj/template-manage/content/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(contentDeleteRequestDTO)))
                .andExpect(status().isOk());

        verify(contentService, times(1)).deleteContent(contentDeleteRequestDTO);
    }
}