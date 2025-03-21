package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.ItemOptionRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemSelectRequestDTO;
import com.onj.template_manage.DTO.Response.SelectedItemResponseDTO;
import com.onj.template_manage.DTO.Response.SelectedItemResponsePagingDTO;
import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemOption;
import com.onj.template_manage.entity.ItemType;
import com.onj.template_manage.exception.Item.ItemNotRegisterFromUserException;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
                .map(item -> {
                    // ItemType이 TEXT인 경우 옵션 리스트를 비운다.
                    SelectedItemResponseDTO dto = new SelectedItemResponseDTO(item);
                    if (item.getType() == ItemType.TEXT) {
                        dto.setSelectedItemOptionResponseDTOList(new ArrayList<>());  // 빈 리스트로 설정
                    }
                    return dto;})
                .collect(Collectors.toList());

        // 페이징 정보와 함께 반환
        return new SelectedItemResponsePagingDTO(selectedItemResponseDTOList, itemPage.getNumber(), itemPage.getSize());
    }

    public void updateItem(ItemRegisterRequestDTO itemRegisterRequestDTO) {

        Optional<Item> item = itemRepository.findByNameContainingIgnoreCase(itemRegisterRequestDTO.getName());

        if(item.isPresent() && item.get().getProvider().equals(itemRegisterRequestDTO.getProvider())) {

            if(item.get().getType() == ItemType.TEXT && itemRegisterRequestDTO.getType() != ItemType.TEXT) {
                Item updateItem = item.get();
                updateItem.setName(itemRegisterRequestDTO.getName());
                updateItem.setType(itemRegisterRequestDTO.getType());

                Item updatedItem = itemRepository.save(updateItem);
                //텍스트 타입이었으나 그외 타입으로 변경 -> 하위옵션 추가 필요
                for (ItemOptionRegisterRequestDTO option : itemRegisterRequestDTO.getOption()) {
                    ItemOption itemOption = ItemOption.builder()
                            .optionValue(option.getName())
                            .item(updatedItem)
                            .build();
                    itemOptionRepository.save(itemOption);
                }
            } else{
                Item updateItem = item.get();
                updateItem.setName(itemRegisterRequestDTO.getName());
                updateItem.setType(itemRegisterRequestDTO.getType());

                itemRepository.save(updateItem);
            }

        } else {
            //아이템이 없거나 자신의아이템이 아님을 알려주는 익셉션
            log.error("등록된 Item 존재하지 않습니다.: {}", itemRegisterRequestDTO.getName());
            throw new ItemNotRegisterFromUserException();
        }
    }
}
