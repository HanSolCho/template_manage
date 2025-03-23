package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.ContentItemDataRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ContentRegisterRequestDTO;
import com.onj.template_manage.DTO.Response.ContentSelectResponseDTO;
import com.onj.template_manage.entity.*;
import com.onj.template_manage.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
    @InjectMocks
    private TemplateService templateService;

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
        Long contentId = 1L;

        Template template = Template.builder()
                .id(1L)
                .name("template1")
                .type("type1")
                .provider("provider1")
                .build();

        Item item = Item.builder()
                .id(1L)
                .name("item1")
                .type(ItemType.DROPDOWN)
                .provider("provider1")
                .build();

        ItemOption itemOption = ItemOption.builder()
                .id(1L)
                .optionValue("Option1")
                .build();

        item.setItemOptions(Arrays.asList(itemOption));
        template.setTemplateItem(Arrays.asList(item));

        Content content = Content.builder()
                .id(contentId)
                .name("content1")
                .template(template)
                .date(new Date())
                .provider("provider1")
                .build();

        // Mocking the repository method
        when(contentRepository.findById(contentId)).thenReturn(Optional.of(content));

        // when
        ContentSelectResponseDTO result = contentService.selectContent(contentId);

        // then
        assertNotNull(result);
        assertEquals(contentId, result.getId());
        assertEquals("content1", result.getName());
        assertNotNull(result.getTemplate());
        assertEquals("template1", result.getTemplate().getName());
        assertNotNull(result.getTemplate().getTemplateItem());
        assertEquals(1, result.getTemplate().getTemplateItem().size());
        assertEquals("item1", result.getTemplate().getTemplateItem().get(0).getName());
        assertNotNull(result.getTemplate().getTemplateItem().get(0).getItemOptions());
        assertEquals(1, result.getTemplate().getTemplateItem().get(0).getItemOptions().size());
        assertEquals("Option1", result.getTemplate().getTemplateItem().get(0).getItemOptions().get(0).getOptionValue());

    }

    @Test
    void updateContent() {
        // Given
        Long contentId = 1L;
        Long templateId = 1L;
        String provider = "provider1";
        ContentRegisterRequestDTO contentRegisterRequestDTO = new ContentRegisterRequestDTO();
        contentRegisterRequestDTO.setId(contentId);
        contentRegisterRequestDTO.setTemplateId(templateId);
        contentRegisterRequestDTO.setProvider(provider);
        contentRegisterRequestDTO.setName("upateName1");

        ContentItemDataRegisterRequestDTO itemData = new ContentItemDataRegisterRequestDTO();
        itemData.setItemId(1L);
        itemData.setValue("updateValue");
        contentRegisterRequestDTO.setItemDataList(Collections.singletonList(itemData));

        Item item = Item.builder()
                .id(1L)
                .name("item1")
                .type(ItemType.DROPDOWN)
                .provider("provider1")
                .build();

        Template template = Template.builder()
                .id(templateId)
                .name("template1")
                .type("type1")
                .accessLevel(AccessLevel.PUBLIC)
                .provider("provider1")
                .isDeleted(false)
                .build();

        Content content = Content.builder()
                .id(contentId)
                .name("content1")
                .template(template)
                .date(new Date())
                .provider("provider1")
                .build();

        ContentItemData contentItemData = ContentItemData.builder()
                .itemValue(itemData.getValue())
                .item(item)
                .content(content)
                .build();

        // Mock behavior
        when(contentRepository.findById(contentId)).thenReturn(Optional.of(content));
        when(templateRepository.findById(templateId)).thenReturn(Optional.of(template));
        when(itemRepository.findById(itemData.getItemId())).thenReturn(Optional.of(item));
        when(contentItemDataRepository.findById(itemData.getItemId())).thenReturn(Optional.of(contentItemData));

        // When
        contentService.updateContent(contentRegisterRequestDTO);

        // Then
        verify(contentRepository).save(content); // Content 객체 저장을 확인
        verify(contentItemDataRepository).save(contentItemData); // ContentItemData 객체 저장을 확인
    }
}