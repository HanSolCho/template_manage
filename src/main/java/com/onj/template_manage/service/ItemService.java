package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.ItemOptionRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemSelectRequestDTO;
import com.onj.template_manage.DTO.Response.SelectedItemResponseDTO;
import com.onj.template_manage.DTO.Response.SelectedItemResponsePagingDTO;
import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemOption;
import com.onj.template_manage.entity.ItemType;
import com.onj.template_manage.repository.ItemOptionRepository;
import com.onj.template_manage.repository.ItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ItemService {

    @Autowired
    public ItemRepository itemRepository;
    @Autowired
    public ItemOptionRepository itemOptionRepository;

    public void registerItem(ItemRegisterRequestDTO itemRegisterRequestDTO) {
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Item item = Item.builder()
                .name(itemRegisterRequestDTO.getName())
                .type(itemRegisterRequestDTO.getType())
                .isDeleted(false)
                .provider(itemRegisterRequestDTO.getProvider())
                .date(currentDate)
                .build();

        Item isNotTextItem = itemRepository.save(item);

        if(!(isNotTextItem.getType() == ItemType.TEXT)) {
            for(ItemOptionRegisterRequestDTO option : itemRegisterRequestDTO.getOption()) {
                ItemOption itemOption = ItemOption.builder()
                        .optionValue(option.getName())
                        .item(isNotTextItem)
                        .build();
                itemOptionRepository.save(itemOption);
            }
        }
        log.info("item 추가 성공: {}", item.getName());
    }

    public SelectedItemResponsePagingDTO selectItems(ItemSelectRequestDTO itemSelectRequestDTO) {
        Pageable pageable = PageRequest.of(itemSelectRequestDTO.getPage(), itemSelectRequestDTO.getSize());

        // QueryDSL을 사용하여 필터링된 아이템 조회
        Page<Item> itemPage = itemRepository.findItemsByFilters(itemSelectRequestDTO.getName(), itemSelectRequestDTO.getType(), itemSelectRequestDTO.getProvider(), pageable);

        // 검색된 데이터를 DTO로 변환
        List<SelectedItemResponseDTO> selectedItemResponseDTOList = itemPage.stream()
                .map(SelectedItemResponseDTO::new)
                .collect(Collectors.toList());

        // 페이징 정보와 함께 반환
        return new SelectedItemResponsePagingDTO(selectedItemResponseDTOList, itemPage.getNumber(), itemPage.getSize());
    }



//    public SelectedItemResponsePagingDTO selectAllItem(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Item> itemPage = itemRepository.findAll(pageable);
//
//        List<SelectedItemResponseDTO> selectedItemResponsDTOList = itemPage.stream()
//                .map(SelectedItemResponseDTO::new)
//                .collect(Collectors.toList());
//
//        return new SelectedItemResponsePagingDTO(selectedItemResponsDTOList, itemPage.getNumber(), itemPage.getSize());
//    }
//
//    public SelectedItemResponsePagingDTO selectItemByProvider(String provider,int page, int size){
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Item> itemPage = itemRepository.findByProvider(provider,pageable); // 데이터 조회
//
//        List<SelectedItemResponseDTO> selectedItemResponsDTOList = itemPage.stream()
//                .map(SelectedItemResponseDTO::new)
//                .collect(Collectors.toList());
//
//        return new SelectedItemResponsePagingDTO(selectedItemResponsDTOList, itemPage.getNumber(), itemPage.getSize());
//    }
//
//    //like조회확인
//    public SelectedItemResponsePagingDTO selectItemByName(String name,int page, int size){
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Item> itemPage = itemRepository.findByNameContainingIgnoreCase(name,pageable); // 데이터 조회
//
//        List<SelectedItemResponseDTO> selectedItemResponsDTOList = itemPage.stream()
//                .map(SelectedItemResponseDTO::new)
//                .collect(Collectors.toList());
//
//        return new SelectedItemResponsePagingDTO(selectedItemResponsDTOList, itemPage.getNumber(), itemPage.getSize());
//    }
//
//    public SelectedItemResponsePagingDTO selectItemByType(ItemType type,int page, int size){
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Item> itemPage = itemRepository.findByType(type,pageable);
//
//        List<SelectedItemResponseDTO> selectedItemResponsDTOList = itemPage.stream()
//                .map(SelectedItemResponseDTO::new)
//                .collect(Collectors.toList());
//
//        return new SelectedItemResponsePagingDTO(selectedItemResponsDTOList, itemPage.getNumber(), itemPage.getSize());
//    }
//
//    public SelectedItemResponsePagingDTO selectItemByTypeAndName(String name, ItemType type,int page, int size){
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Item> itemPage = itemRepository.findByTypeAndName(type,name,pageable);
//
//        List<SelectedItemResponseDTO> selectedItemResponsDTOList = itemPage.stream()
//                .map(SelectedItemResponseDTO::new)
//                .collect(Collectors.toList());
//
//        return new SelectedItemResponsePagingDTO(selectedItemResponsDTOList, itemPage.getNumber(), itemPage.getSize());
//    }



}
