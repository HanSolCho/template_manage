package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.ContentItemDataRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ContentRegisterRequestDTO;
import com.onj.template_manage.entity.Content;
import com.onj.template_manage.entity.ContentItemData;
import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.Template;
import com.onj.template_manage.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ContentServiceTest {

    @Mock
    private TemplateRepository templateRepository;

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ContentItemDataRepository contentItemDataRepository;

    @InjectMocks
    private ContentService contentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerContent() {
        ContentRegisterRequestDTO contentRegisterRequestDTO = new ContentRegisterRequestDTO();
        contentRegisterRequestDTO.setName("contentName1");
        contentRegisterRequestDTO.setProvider("provider1");
        contentRegisterRequestDTO.setTemplateId(1L);

        ContentItemDataRegisterRequestDTO contentItemData1 = new ContentItemDataRegisterRequestDTO();
        contentItemData1.setItemId(1L);
        contentItemData1.setValue("itemValue1");

        contentRegisterRequestDTO.setItemDataList(Arrays.asList(contentItemData1));

        // Mocking Template 객체
        Template template = new Template();
        template.setId(1L);
        when(templateRepository.findById(1L)).thenReturn(Optional.of(template));

        // Mocking Item 객체
        Item item = Item.builder()
                .id(1L)
                .build();
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // Mocking ContentRepository 저장
        Content content = Content.builder()
                .name(contentRegisterRequestDTO.getName())
                .provider(contentRegisterRequestDTO.getProvider())
                .template(template)
                .build();
        when(contentRepository.save(any(Content.class))).thenReturn(content);

        // Mocking ContentItemDataRepository 저장
        ContentItemData contentItemData = ContentItemData.builder()
                .itemValue(contentItemData1.getValue())
                .content(content)
                .item(item)
                .build();
        when(contentItemDataRepository.save(any(ContentItemData.class))).thenReturn(contentItemData);

        // When: registerContent 메서드 호출
        contentService.registerContent(contentRegisterRequestDTO);

        // Then: 결과 확인
        verify(contentRepository, times(1)).save(any(Content.class));
        verify(contentItemDataRepository, times(1)).save(any(ContentItemData.class));
    }

    @Test
    void selectContent() {
    }
}