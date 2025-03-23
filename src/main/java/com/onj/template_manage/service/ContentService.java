package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.ContentItemDataRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ContentRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemOptionRegisterRequestDTO;
import com.onj.template_manage.DTO.Request.ItemRegisterRequestDTO;
import com.onj.template_manage.entity.*;
import com.onj.template_manage.repository.ContentItemDataRepository;
import com.onj.template_manage.repository.ContentRepository;
import com.onj.template_manage.repository.ItemRepository;
import com.onj.template_manage.repository.TemplateRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
        for (ContentItemDataRegisterRequestDTO contentItemData : contentRegisterRequestDTO.getItemDataList()) {
            // ContentItemData 객체 저장
            // ITEM의 ID , ITEM의 VALUE
            Item item = itemRepository.findById(contentItemData.getItemId()).orElse(null);
            ContentItemData contentItemData1 = ContentItemData.builder()
                    .itemValue(contentItemData.getValue()) //이값은 text 타입일 경우 입력값, 혹은 checkbox,dropbox의선택한 값이 될것
                    .content(content)
                    .item(item)  // 실제 Item 객체는 그대로 사용
                    .build();
            contentItemDataRepository.save(contentItemData1);
        }

        log.info("item 추가 성공: {}", content.getName());
    }

    public void selectContent(Long contentId) {
        contentRepository.findById(contentId).orElse(null);
        // 나온 데이터 보고 부족한 데이터 추가로 뽑아서
        // DTO 만들어서 담아서 돌려주기
    }
}
