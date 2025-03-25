package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.content.ContentDeleteRequestDTO;
import com.onj.template_manage.DTO.Request.content.ContentItemDataRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.content.ContentRegisterRequestDTO;
import com.onj.template_manage.DTO.Response.content.*;
import com.onj.template_manage.entity.*;
import com.onj.template_manage.exception.Item.ItemNotRegisterFromUserException;
import com.onj.template_manage.exception.content.ContentNotRegisterFromUserException;
import com.onj.template_manage.repository.ContentItemDataRepository;
import com.onj.template_manage.repository.ContentRepository;
import com.onj.template_manage.repository.ItemRepository;
import com.onj.template_manage.repository.TemplateRepository;
import jakarta.transaction.Transactional;
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
public class ContentService {

    @Autowired
    public ContentRepository contentRepository;
    @Autowired
    public ContentItemDataRepository contentItemDataRepository;
    @Autowired
    public TemplateRepository templateRepository;
    @Autowired
    public ItemRepository itemRepository;

    @Transactional
    public void registerContent(ContentRegisterRequestDTO contentRegisterRequestDTO) {
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        // 템플릿을 가져오고 DTO로 변환
        Template template = templateRepository.findById(contentRegisterRequestDTO.getTemplateId()).orElse(null);

        // Content 객체 생성
        Content content = Content.builder()
                .name(contentRegisterRequestDTO.getName())
                .provider(contentRegisterRequestDTO.getProvider())
                .date(currentDate)
                .template(template)  // DTO를 사용하여 Template 정보 설정
                .build();

        // Content 저장
        contentRepository.save(content);

//        // ContentItemData 처리
        for (ContentItemDataRegisterRequestDTO contentItemDataRegisterRequestDTO : contentRegisterRequestDTO.getItemDataList()) {
            // ContentItemData 객체 저장
            // ITEM의 ID , ITEM의 VALUE
            Item item = itemRepository.findById(contentItemDataRegisterRequestDTO.getItemId()).orElse(null);
            ContentItemData contentItemData = ContentItemData.builder()
                    .itemValue(contentItemDataRegisterRequestDTO.getValue()) //이값은 text 타입일 경우 입력값, 혹은 checkbox,dropbox의선택한 값이 될것
                    .content(content)
                    .item(item)  // 실제 Item 객체는 그대로 사용
                    .build();
            contentItemDataRepository.save(contentItemData);
        }

        log.info("item 추가 성공: {}", content.getName());
    }
    // 페이징 추가해주자
    public List<ContentListResponseDTO> selectContentList(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Content> contentPage = contentRepository.findAll(pageable);

        List<ContentListResponseDTO> contentListResponseDTOS = contentPage.stream()
                .map(content -> {
                    ContentListResponseDTO contentListResponseDTO = new ContentListResponseDTO();
                    contentListResponseDTO.setId(content.getId());
                    contentListResponseDTO.setName(content.getName());
                    return contentListResponseDTO;
                })
                .collect(Collectors.toList());
        return contentListResponseDTOS;
    }

    public ContentSelectResponseDTO selectContent(Long contentId) {
        Content content = contentRepository.findById(contentId).orElse(null);
        if (content != null) {
            ContentSelectResponseDTO contentSelectResponseDTO = new ContentSelectResponseDTO();
            contentSelectResponseDTO.setId(content.getId());
            contentSelectResponseDTO.setName(content.getName());

            Template template = content.getTemplate();
            ContentSelectTemplateResponseDTO contentSelectTemplateResponseDTO = new ContentSelectTemplateResponseDTO();
            contentSelectTemplateResponseDTO.setId(template.getId());
            contentSelectTemplateResponseDTO.setName(template.getName());
            contentSelectTemplateResponseDTO.setProvider(template.getProvider());
            contentSelectTemplateResponseDTO.setType(template.getType());

            //titem =  template.getTemplateItem()
            List<ContentSelectItemResponseDTO> contentItemResponseDTOList = new ArrayList<>();
            for (Item item : template.getTemplateItem()) {
                ContentSelectItemResponseDTO contentSelectItemResponseDTO = new ContentSelectItemResponseDTO(item);
                contentSelectItemResponseDTO.setId(item.getId());
                contentSelectItemResponseDTO.setName(item.getName());
                contentSelectItemResponseDTO.setType(item.getType());
                contentSelectItemResponseDTO.setProvider(item.getProvider());

                ContentItemData contentItemData = contentItemDataRepository.findByContentIdAndItemId(content.getId(),item.getId());

                contentSelectItemResponseDTO.setItemOptionValue(contentItemData.getItemValue());
                contentItemResponseDTOList.add(contentSelectItemResponseDTO);
            }

            contentSelectTemplateResponseDTO.setTemplateItem(contentItemResponseDTOList);
            contentSelectResponseDTO.setTemplate(contentSelectTemplateResponseDTO);

            return contentSelectResponseDTO;
        } else {
            //아이템이 없거나 자신의아이템이 아님을 알려주는 익셉션
            log.error("content Id에 해당하는 컨텐츠는 존재하지 않습니다.: {}", contentId);
            throw new ItemNotRegisterFromUserException();
        }
    }

    public void updateContent(ContentRegisterRequestDTO contentRegisterRequestDTO) {

        Optional<Content> selectTemplate = contentRepository.findById(contentRegisterRequestDTO.getId());
        // 템플릿을 가져오고 DTO로 변환
        Template template = templateRepository.findById(contentRegisterRequestDTO.getTemplateId()).orElse(null);
        if (selectTemplate.isPresent() && selectTemplate.get().getProvider().equals(contentRegisterRequestDTO.getProvider())) {
            Content content = selectTemplate.get();
            content.setName(contentRegisterRequestDTO.getName());
            content.setTemplate(template);

            for (ContentItemDataRegisterRequestDTO contentItemDataUpdateRequestDTO : contentRegisterRequestDTO.getItemDataList()) {
                Item item = itemRepository.findById(contentItemDataUpdateRequestDTO.getItemId()).orElse(null);
                ContentItemData contentItemData = contentItemDataRepository.findById(contentItemDataUpdateRequestDTO.getItemId()).orElse(null);
                contentItemData.setItemValue(contentItemDataUpdateRequestDTO.getValue());
                contentItemData.setContent(content);
                contentItemData.setItem(item);
                contentItemDataRepository.save(contentItemData);
            }
            contentRepository.save(content);
        }

    }

    public void deleteContent(ContentDeleteRequestDTO contentDeleteRequestDTO) {
        Content content = contentRepository.findById(contentDeleteRequestDTO.getId()).orElse(null);

        if(content != null && content.getProvider().equals(contentDeleteRequestDTO.getProvider())) {
            contentRepository.delete(content);
        }
        else{
            throw new ContentNotRegisterFromUserException();
        }
    }
}
