package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.*;
import com.onj.template_manage.DTO.Response.*;
import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.Template;
import com.onj.template_manage.exception.Item.ItemOptionIsNullException;
import com.onj.template_manage.repository.ItemRepository;
import com.onj.template_manage.repository.TemplateRepository;
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
public class TemplateService {

    @Autowired
    public TemplateRepository templateRepository;
    @Autowired
    public ItemRepository itemRepository;


    public void registerTemplate(TemplateRegisterRequestDTO templateRegisterRequestDTO) {
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Template template = Template.builder()
                .name(templateRegisterRequestDTO.getName())
                .type(templateRegisterRequestDTO.getType())
                .accessLevel(templateRegisterRequestDTO.getAccessLevel())
                .provider(templateRegisterRequestDTO.getProvider())
                .isDeleted(false)
                .templateItem(new ArrayList<>())
                .date(currentDate)
                .build();

        Template registerTemplate = templateRepository.save(template);
        for(TemplateItemRegisterRequestDTO itemRequestDTO : templateRegisterRequestDTO.getItem()){
            Optional<Item> selectedItem = itemRepository.findById(itemRequestDTO.getItemId());
            if (selectedItem.isPresent()) {
                Item item = selectedItem.get();
                registerTemplate.getTemplateItem().add(item);  // 템플릿에 아이템 추가
                item.getTemplates().add(registerTemplate);     // 아이템에 템플릿 추가

                // 아이템 저장 (아이템에서 템플릿 정보까지 반영)
                itemRepository.save(item);
            } else {
                // 아이템이 존재하지 않으면 예외 처리 (선택사항)
                throw new ItemOptionIsNullException();
            }
        }
        // 템플릿을 다시 저장하여 연관된 아이템들과의 관계 반영
        templateRepository.save(registerTemplate);
    }


    public SelectedTemplateResponsePagingDTO selectTemplate(TemplateSelectRequestDTO templateSelectRequestDTO) {
        Pageable pageable = PageRequest.of(templateSelectRequestDTO.getPage(), templateSelectRequestDTO.getSize());

        // QueryDSL을 사용하여 필터링된 아이템 조회
        Page<Template> templatePage = templateRepository.findTemplateByFilters(templateSelectRequestDTO.getName(), templateSelectRequestDTO.getType(),  templateSelectRequestDTO.getProvider(), templateSelectRequestDTO.getAccessLevel(), pageable);

        // 검색된 데이터를 DTO로 변환
        List<SelectedTemplateResponseDTO> selectedTemplateResponseDTOList = templatePage.stream()
                .map(SelectedTemplateResponseDTO::new)
                .collect(Collectors.toList());

        // 페이징 정보와 함께 반환
        return new SelectedTemplateResponsePagingDTO(selectedTemplateResponseDTOList, templatePage.getNumber(), templatePage.getSize());
    }
}
