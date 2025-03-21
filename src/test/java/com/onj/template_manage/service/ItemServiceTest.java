package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.ItemSelectRequestDTO;
import com.onj.template_manage.DTO.Response.SelectedItemResponsePagingDTO;
import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemType;
import com.onj.template_manage.entity.User;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class) // Mockito와 Spring Test 통합
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void registerItem() {
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
}