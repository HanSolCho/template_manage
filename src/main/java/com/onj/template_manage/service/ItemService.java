package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.item.ItemDeleteRequestDTO;
import com.onj.template_manage.DTO.Request.item.ItemOptionRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.item.ItemRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.item.ItemSelectRequestDTO;
import com.onj.template_manage.DTO.Response.Item.SelectedItemOptionResponseDTO;
import com.onj.template_manage.DTO.Response.Item.SelectedItemResponseDTO;
import com.onj.template_manage.DTO.Response.Item.SelectedItemResponsePagingDTO;
import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemOption;
import com.onj.template_manage.entity.ItemType;
import com.onj.template_manage.exception.Item.ItemNotRegisterFromUserException;
import com.onj.template_manage.exception.Item.ItemOptionIsNullException;
import com.onj.template_manage.repository.ItemOptionRepository;
import com.onj.template_manage.repository.ItemRepository;
import jakarta.transaction.Transactional;
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

                    // ItemOption의 isDeleted가 false인 값만 필터링하여 리스트 설정
                    List<SelectedItemOptionResponseDTO> filteredOptions = item.getItemOptions().stream()
                            .filter(option -> !option.getIsDeleted())  // isDeleted가 false인 값만 필터링
                            .map(option -> new SelectedItemOptionResponseDTO(option))
                            .collect(Collectors.toList());

                    dto.setSelectedItemOptionResponseDTOList(filteredOptions);  // 필터링된 옵션을 설정

                    return dto;
                })
                .collect(Collectors.toList());

        // 페이징 정보와 함께 반환
        return new SelectedItemResponsePagingDTO(selectedItemResponseDTOList, itemPage.getNumber(), itemPage.getSize());
    }

    @Transactional
    public void updateItem(ItemRegisterRequestDTO itemRegisterRequestDTO) {

        Optional<Item> item = itemRepository.findById(itemRegisterRequestDTO.getId());
        if(item.isPresent() && item.get().getProvider().equals(itemRegisterRequestDTO.getProvider())) {

            if(item.get().getType() == ItemType.TEXT && itemRegisterRequestDTO.getType() != ItemType.TEXT) {
                Item updateItem = item.get();
                updateItem.setName(itemRegisterRequestDTO.getName());
                updateItem.setType(itemRegisterRequestDTO.getType());

                Item updatedItem = itemRepository.save(updateItem);
                if (itemRegisterRequestDTO.getOption() == null || itemRegisterRequestDTO.getOption().isEmpty()) {
                    throw new ItemOptionIsNullException();
                }
                for (ItemOptionRegisterRequestDTO option : itemRegisterRequestDTO.getOption()) {
                    ItemOption itemOption = ItemOption.builder()
                            .optionValue(option.getName())
                            .item(updatedItem)
                            .build();
                    itemOptionRepository.save(itemOption);
                }
            } else if (item.get().getType() != ItemType.TEXT && itemRegisterRequestDTO.getType() == ItemType.TEXT) {
                //텍스트 이외의타입이 텍스트로 변경시 하위 옵션 소프트삭제 진행
                Item updateItem = item.get();
                updateItem.setName(itemRegisterRequestDTO.getName());
                updateItem.setType(itemRegisterRequestDTO.getType());

                List<ItemOption> itemOptionList = itemOptionRepository.findByItemId(updateItem.getId());

                for(ItemOption itemOption : itemOptionList) {
                    itemOption.setIsDeleted(true);
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

    public void softDeleteItem(ItemDeleteRequestDTO itemDeleteRequestDTO) {
        Optional<Item> item = itemRepository.findById(itemDeleteRequestDTO.getId());
        if(item.isPresent() && item.get().getProvider().equals(itemDeleteRequestDTO.getProvider())) {
            Item deleteItem = item.get();
            deleteItem.setIsDeleted(true);
            itemRepository.save(deleteItem);

            List<ItemOption> itemOptionList = itemOptionRepository.findByItemId(deleteItem.getId());

            for(ItemOption itemOption : itemOptionList) {
                itemOption.setIsDeleted(true);
                itemOptionRepository.save(itemOption);
            }

        } else {
        //아이템이 없거나 자신의아이템이 아님을 알려주는 익셉션
        log.error("등록된 Item 존재하지 않습니다.: {}", itemDeleteRequestDTO.getId());
        throw new ItemNotRegisterFromUserException();
        }
    }
}
