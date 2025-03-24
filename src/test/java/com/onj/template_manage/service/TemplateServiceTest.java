package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.*;
import com.onj.template_manage.DTO.Response.SelectedItemResponsePagingDTO;
import com.onj.template_manage.DTO.Response.SelectedTemplateResponsePagingDTO;
import com.onj.template_manage.entity.*;
import com.onj.template_manage.exception.template.TemplateUseInContentException;
import com.onj.template_manage.repository.ContentRepository;
import com.onj.template_manage.repository.ItemOptionRepository;
import com.onj.template_manage.repository.ItemRepository;
import com.onj.template_manage.repository.TemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TemplateServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private TemplateRepository templateRepository;
    @Mock
    private ContentRepository contentRepository;

    @InjectMocks
    private TemplateService templateService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerTemplate() {

        TemplateRegisterRequestDTO templateRegisterRequestDTO = new TemplateRegisterRequestDTO();
        templateRegisterRequestDTO.setName("Sample Template");
        templateRegisterRequestDTO.setType("TypeA");
        templateRegisterRequestDTO.setAccessLevel(AccessLevel.PUBLIC);
        templateRegisterRequestDTO.setProvider("TestProvider");
        templateRegisterRequestDTO.setItem(new ArrayList<>());

        TemplateItemRegisterRequestDTO templateItemRegisterRequestDTO = new TemplateItemRegisterRequestDTO();
        templateItemRegisterRequestDTO.setItemId(1L);

        templateRegisterRequestDTO.getItem().add(templateItemRegisterRequestDTO);

        Item item = Item.builder()
                .id(1L)
                .name("Sample Item")
                .type(ItemType.TEXT)
                .provider("TestProvider")
                .isDeleted(false)
                .date(new Date())
                .itemOptions(new ArrayList<>())
                .templates(new ArrayList<>())  // templates를 빈 리스트로 초기화
                .build();

        Template template = Template.builder()
                .id(1L)
                .name("Sample Template")
                .type("TypeA")
                .accessLevel(AccessLevel.PUBLIC)
                .provider("TestProvider")
                .isDeleted(false)
                .templateItem(new ArrayList<>())
                .build();

        // Mocking ItemRepository findById() 메서드
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // Mocking TemplateRepository save() 메서드
        when(templateRepository.save(any(Template.class))).thenReturn(template);

        // 메서드 호출
        templateService.registerTemplate(templateRegisterRequestDTO);

        // 결과 검증
        assertNotNull(template.getTemplateItem());
        assertTrue(template.getTemplateItem().contains(item));  // 템플릿에 아이템이 포함되어야 함
        assertTrue(item.getTemplates().contains(template));  // 아이템에 템플릿이 포함되어야 함

        // verify save() 메서드가 호출되었는지 확인
        verify(templateRepository, times(2)).save(any(Template.class));  // 두 번 호출되어야 함: 한 번은 템플릿 저장, 두 번째는 템플릿과 아이템 연결 후 저장
        verify(itemRepository, times(1)).save(any(Item.class));  // 아이템은 한 번만 저장되어야 함
    }

    @Test
    void selectTemplate() {
        //given
        Template template1 = Template.builder()
                .id(1L)
                .name("template1")
                .type("type1")
                .provider("Provider1")
                .accessLevel(AccessLevel.PUBLIC)
                .isDeleted(false)
                .date(new Date())
                .build();

        Template template2 = Template.builder()
                .id(2L)
                .name("template2")
                .type("type2")
                .provider("Provider2")
                .accessLevel(AccessLevel.PUBLIC)
                .isDeleted(false)
                .date(new Date())
                .build();

        TemplateSelectRequestDTO templateSelectRequestDTO = new TemplateSelectRequestDTO();
        templateSelectRequestDTO.setName("template1");
        templateSelectRequestDTO.setType("type1");
        templateSelectRequestDTO.setProvider("Provider1");
        templateSelectRequestDTO.setAccessLevel(AccessLevel.PUBLIC);
        templateSelectRequestDTO.setPage(0);
        templateSelectRequestDTO.setSize(10);

        // Mocking the repository method
        List<Template> templates = Arrays.asList(template1, template2);
        Page<Template> templatePage = new PageImpl<>(templates, PageRequest.of(templateSelectRequestDTO.getPage(), templateSelectRequestDTO.getSize()), templates.size());
        when(templateRepository.findTemplateByFilters(templateSelectRequestDTO.getName(), templateSelectRequestDTO.getType(), templateSelectRequestDTO.getProvider(), templateSelectRequestDTO.getAccessLevel(), PageRequest.of(templateSelectRequestDTO.getPage(), templateSelectRequestDTO.getSize()))).thenReturn(templatePage);

        // When
        SelectedTemplateResponsePagingDTO result = templateService.selectTemplate(templateSelectRequestDTO);
        // Then
        assertNotNull(result);
        assertEquals(2, result.getTemplateS().size());
        assertEquals("template1", result.getTemplateS().get(0).getName());
        assertEquals("template2", result.getTemplateS().get(1).getName());
        assertEquals(templateSelectRequestDTO.getPage(), result.getNumber());
        assertEquals(templateSelectRequestDTO.getSize(), result.getSize());
    }

    @Test
    void updateTemplate() {
        Template template = Template.builder()
                .id(1L)
                .name("template1")
                .type("type1")
                .provider("provider1")
                .accessLevel(AccessLevel.PUBLIC)
                .isDeleted(false)
                .date(new Date())
                .templateItem(new ArrayList<>())
                .build();

        Item item = Item.builder()
                .id(1L)
                .name("item1")
                .type(ItemType.TEXT)
                .provider("provider1")
                .isDeleted(false)
                .date(new Date())
                .templates(new ArrayList<>())
                .build();

        TemplateRegisterRequestDTO requestDTO = new TemplateRegisterRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setName("updateTemplate");
        requestDTO.setType("Updated Type");
        requestDTO.setProvider("provider1");
        requestDTO.setAccessLevel(AccessLevel.PUBLIC);

        TemplateItemRegisterRequestDTO itemDTO = new TemplateItemRegisterRequestDTO();
        itemDTO.setItemId(1L);
        requestDTO.setItem(Collections.singletonList(itemDTO));

        // When
        when(templateRepository.findById(1L)).thenReturn(Optional.of(template));
        when(contentRepository.findByTemplateId(1L)).thenReturn(Collections.emptyList());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // 템플릿 업데이트 실행
        templateService.updateTemplate(requestDTO);

        // Then
        verify(templateRepository).save(template);
        verify(itemRepository).save(item);
    }

    @Test
    void softDeleteTemplate() {
        // Given
        Long templateId = 1L;
        String provider = "provider1";

        TemplateDeleteRequsetDTO templateDeleteRequsetDTO = new TemplateDeleteRequsetDTO();
        templateDeleteRequsetDTO.setId(templateId);
        templateDeleteRequsetDTO.setProvider(provider);

        Template template = Template.builder()
                .id(templateId)
                .name("template1")
                .provider(provider)
                .isDeleted(false)
                .build();

        when(templateRepository.findById(templateId)).thenReturn(Optional.of(template));

        // When
        templateService.softDeleteTemplate(templateDeleteRequsetDTO);

        // Then
        assertTrue(template.getIsDeleted());
        verify(templateRepository).save(template);
    }

}