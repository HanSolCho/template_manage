package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.ItemDeleteRequestDTO;
import com.onj.template_manage.DTO.Request.ItemOptionRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemSelectRequestDTO;
import com.onj.template_manage.DTO.Response.SelectedItemResponsePagingDTO;
import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemOption;
import com.onj.template_manage.entity.ItemType;
import com.onj.template_manage.entity.User;
import com.onj.template_manage.exception.Item.ItemNotRegisterFromUserException;
import com.onj.template_manage.repository.ItemOptionRepository;
import com.onj.template_manage.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemOptionRepository itemOptionRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void registerItemTextType() {
        // Given: 필요한 데이터를 준비합니다.
        Item item = Item.builder()
                .id(1L)
                .name("Item1")
                .type(ItemType.TEXT)
                .provider("Provider1")
                .isDeleted(false)
                .date(new Date())
                .build();

        ItemRegisterRequestDTO itemRegisterRequestDTO = new ItemRegisterRequestDTO();
        itemRegisterRequestDTO.setName("Item1");
        itemRegisterRequestDTO.setProvider("Provider1");
        itemRegisterRequestDTO.setType(ItemType.TEXT);
        itemRegisterRequestDTO.setOption(new ArrayList<>());

        // When: registerItem 메소드 실행
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        itemService.registerItem(itemRegisterRequestDTO);

        // Then: itemRepository.save()가 호출되어 아이템이 저장되었는지 확인
        verify(itemRepository, times(1)).save(any(Item.class));
        // itemOptionRepository.save()는 호출되지 않아야 합니다.
        verify(itemOptionRepository, never()).save(any(ItemOption.class));
    }

    @Test
    public void registerItemNonTextType() {
        // Given:
        Item item = Item.builder()
                .id(1L)
                .name("Item1")
                .type(ItemType.CHECKBOX)
                .provider("Provider1")
                .isDeleted(false)
                .date(new Date())
                .build();

        // ItemOptionRegisterRequestDTO 리스트 생성
        List<ItemOptionRegisterRequestDTO> options = new ArrayList<>();
        ItemOptionRegisterRequestDTO option1 = new ItemOptionRegisterRequestDTO();
        option1.setName("Option1");
        options.add(option1);


        ItemRegisterRequestDTO itemRegisterRequestDTO = new ItemRegisterRequestDTO();
        itemRegisterRequestDTO.setName("Item1");
        itemRegisterRequestDTO.setProvider("Provider1");
        itemRegisterRequestDTO.setType(ItemType.CHECKBOX);
        itemRegisterRequestDTO.setOption(options);

        // When: registerItem 메소드 실행
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        itemService.registerItem(itemRegisterRequestDTO);

        // Then: itemRepository.save()가 호출되어 아이템이 저장되었는지 확인
        verify(itemRepository, times(1)).save(any(Item.class));
        // itemOptionRepository.save()가 호출되어 아이템 옵션이 저장되었는지 확인
        verify(itemOptionRepository, times(1)).save(any(ItemOption.class));
    }

    @Test
    public void registerItemNonTextTypeNoOption() {
        // Given:
        Item item = Item.builder()
                .id(1L)
                .name("Item1")
                .type(ItemType.CHECKBOX)
                .provider("Provider1")
                .isDeleted(false)
                .date(new Date())
                .build();

        ItemRegisterRequestDTO itemRegisterRequestDTO = new ItemRegisterRequestDTO();
        itemRegisterRequestDTO.setName("Item1");
        itemRegisterRequestDTO.setProvider("Provider1");
        itemRegisterRequestDTO.setType(ItemType.CHECKBOX);
        itemRegisterRequestDTO.setOption(new ArrayList<>());

        // When: registerItem 메소드 실행
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        itemService.registerItem(itemRegisterRequestDTO);

        // Then: itemRepository.save()가 호출되어 아이템이 저장되었는지 확인
        verify(itemRepository, times(1)).save(any(Item.class));
        // itemOptionRepository.save()는 호출되지 않아야 합니다.
        verify(itemOptionRepository, never()).save(any(ItemOption.class));
    }

    @Test
    void selectItems() {
        //given
        Item item1 = Item.builder()
                .id(1L)
                .name("Item1")
                .type(ItemType.TEXT)
                .provider("Provider1")
                .isDeleted(false)
                .date(new Date())
                .build();

        Item item2 = Item.builder()
                .id(2L)
                .name("Item2")
                .type(ItemType.CHECKBOX)
                .provider("Provider2")
                .isDeleted(false)
                .date(new Date())
                .build();
        ItemSelectRequestDTO itemSelectRequestDTO = new ItemSelectRequestDTO();
        itemSelectRequestDTO.setName("Item1");
        itemSelectRequestDTO.setType(ItemType.TEXT);
        itemSelectRequestDTO.setProvider("Provider1");
        itemSelectRequestDTO.setPage(0);
        itemSelectRequestDTO.setSize(10);


        // Mocking the repository method
        List<Item> items = Arrays.asList(item1, item2);
        Page<Item> itemPage = new PageImpl<>(items, PageRequest.of(itemSelectRequestDTO.getPage(), itemSelectRequestDTO.getSize()), items.size());
        when(itemRepository.findItemsByFilters(itemSelectRequestDTO.getName(), itemSelectRequestDTO.getType(), itemSelectRequestDTO.getProvider(), PageRequest.of(itemSelectRequestDTO.getPage(), itemSelectRequestDTO.getSize()))).thenReturn(itemPage);

        // When
        SelectedItemResponsePagingDTO result = itemService.selectItems(itemSelectRequestDTO);
        // Then
        assertNotNull(result);
        assertEquals(2, result.getItems().size());
        assertEquals("Item1", result.getItems().get(0).getName());
        assertEquals("Item2", result.getItems().get(1).getName());
        assertEquals(itemSelectRequestDTO.getPage(), result.getNumber());
        assertEquals(itemSelectRequestDTO.getSize(), result.getSize());
    }

    @Test
    void updateItemChangeType() {

        // Given:
        Item existingItem = Item.builder()
                .id(1L)
                .name("Item1")
                .provider("Provider1")
                .type(ItemType.TEXT)
                .build();

        List<ItemOptionRegisterRequestDTO> options = new ArrayList<>();
        ItemOptionRegisterRequestDTO option1 = new ItemOptionRegisterRequestDTO();
        option1.setName("Option1");
        options.add(option1);

        ItemRegisterRequestDTO itemRegisterRequestDTO = new ItemRegisterRequestDTO();
        itemRegisterRequestDTO.setId(1L);
        itemRegisterRequestDTO.setName("Item1");
        itemRegisterRequestDTO.setProvider("Provider1");
        itemRegisterRequestDTO.setType(ItemType.CHECKBOX);
        itemRegisterRequestDTO.setOption(options);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem)); // 아이템 조회

        Item updatedItem = Item.builder()
                .id(1L)
                .name("Item1")
                .provider("Provider1")
                .type(ItemType.CHECKBOX)
                .build();
        when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);
        when(itemOptionRepository.save(any(ItemOption.class))).thenReturn(mock(ItemOption.class));

        // When: updateItem 메소드 실행
        itemService.updateItem(itemRegisterRequestDTO);

        // Then: itemRepository.save()가 호출되어 기존 아이템이 저장되어야 함
        verify(itemRepository, times(1)).save(any(Item.class));  // 아이템이 저장되어야 함
        // 기존 아이템의 타입이 TEXT였고 새로 업데이트된 타입이 TEXT가 아니므로 옵션을 저장해야 함
        verify(itemOptionRepository, times(1)).save(any(ItemOption.class));  // 옵션 저장되어야 함
    }

    @Test
    void updateItemItemNotFound() {
        // Given: 아이템이 존재하지 않거나 제공자가 일치하지 않는 경우
        ItemRegisterRequestDTO itemRegisterRequestDTO = new ItemRegisterRequestDTO();
        itemRegisterRequestDTO.setId(1L);
        itemRegisterRequestDTO.setName("ItemNotExist");
        itemRegisterRequestDTO.setProvider("Provider1");
        itemRegisterRequestDTO.setType(ItemType.CHECKBOX);

        // Mocking: 아이템이 존재하지 않음
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then: 아이템이 존재하지 않으면 예외가 발생해야 함
        assertThrows(ItemNotRegisterFromUserException.class,
                () -> itemService.updateItem(itemRegisterRequestDTO));
    }

    @Test
    void updateItemDifferentProvider() {
        // Given: 아이템이 존재하고 제공자가 일치하지 않는 경우
        Item existingItem = Item.builder()
                .id(1L)
                .name("Item1")
                .provider("Provider2")
                .type(ItemType.TEXT)
                .build();

        ItemRegisterRequestDTO itemRegisterRequestDTO = new ItemRegisterRequestDTO();
        itemRegisterRequestDTO.setId(1L);
        itemRegisterRequestDTO.setName("Item1");
        itemRegisterRequestDTO.setProvider("Provider1");
        itemRegisterRequestDTO.setType(ItemType.CHECKBOX);

        // Mocking: 아이템이 존재하지만 제공자가 일치하지 않음
        when(itemRepository.findById(1L)).thenReturn(Optional.ofNullable(existingItem));

        // When & Then: 제공자가 일치하지 않으면 예외가 발생해야 함
        ItemNotRegisterFromUserException exception = assertThrows(ItemNotRegisterFromUserException.class,
                () -> itemService.updateItem(itemRegisterRequestDTO));

        assertNotNull(exception);
    }

    @Test
    void softDeleteItem() {
        ItemDeleteRequestDTO itemDeleteRequestDTO = new ItemDeleteRequestDTO();
        itemDeleteRequestDTO.setId(1L);
        itemDeleteRequestDTO.setProvider("Provider1");

        Item item = Item.builder()
                .id(1L)
                .provider("Provider1")
                .isDeleted(false)
                .build();

        when(itemRepository.findById(itemDeleteRequestDTO.getId())).thenReturn(Optional.of(item));

        ItemOption itemOption = ItemOption.builder()
                .id(1L)
                .item(item)
                .isDeleted(false)
                .build();

        List<ItemOption> itemOptionList = List.of(itemOption);

        when(itemOptionRepository.findByItemId(item.getId())).thenReturn(itemOptionList);
        // 실제 메서드 실행
        itemService.softDeleteItem(itemDeleteRequestDTO);

        // 검증
        verify(itemRepository, times(1)).save(item);
        assertTrue(item.getIsDeleted(), "아이템 소프트 삭제");

        for (ItemOption tempItemOption : itemOptionList) {
            verify(itemOptionRepository, times(1)).save(tempItemOption);
            assertTrue(tempItemOption.getIsDeleted(), "아이템 옵션 소프트 삭제");
        }
    }
}