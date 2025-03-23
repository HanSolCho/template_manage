package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.*;
import com.onj.template_manage.DTO.Response.*;
import com.onj.template_manage.entity.Content;
import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemOption;
import com.onj.template_manage.entity.Template;
import com.onj.template_manage.exception.Item.ItemNotRegisterFromUserException;
import com.onj.template_manage.exception.Item.ItemOptionIsNullException;
import com.onj.template_manage.exception.template.TemplateNotRegisterFromUserException;
import com.onj.template_manage.exception.template.TemplateUseInContentException;
import com.onj.template_manage.repository.ContentRepository;
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
    @Autowired
    public ContentRepository contentRepository;


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

    public void updateTemplate(TemplateRegisterRequestDTO templateRegisterRequestDTO) {

        Optional<Template> selectTemplate = templateRepository.findById(templateRegisterRequestDTO.getId());
        List<Optional<Content>> content = contentRepository.findByTemplateId(templateRegisterRequestDTO.getId());
        //템플릿이 존재하고, 해당 템플릿 생산자가 요청자와 동일해야해
        if(content.isEmpty() && selectTemplate.isPresent() && selectTemplate.get().getProvider().equals(templateRegisterRequestDTO.getProvider())) {
            Template template = selectTemplate.get();
            template.setName(templateRegisterRequestDTO.getName());
            template.setType(templateRegisterRequestDTO.getType());
            template.setProvider(templateRegisterRequestDTO.getProvider());
            template.setAccessLevel(templateRegisterRequestDTO.getAccessLevel());

            // 기존 아이템 관계를 업데이트하려면, 먼저 템플릿의 기존 아이템을 모두 삭제해야 함
            List<Item> currentItems = new ArrayList<>(template.getTemplateItem());  // 현재 템플릿에 연결된 아이템들
            // 기존 아이템들을 제거 (아이템과 템플릿 간의 관계도 제거)
            for (Item currentItem : currentItems) {
                template.getTemplateItem().remove(currentItem);
                currentItem.getTemplates().remove(template);
                itemRepository.save(currentItem);  // 아이템의 템플릿 관계 업데이트
            }
            //변경이아닌 추가가되고있음 수정 -> 추가가맞을지 변경이 맞을지 고민이 필요. -> 소프트삭제이기에 둘 사이 연관관계는 유지시켜야할 필요가있을수 있음
            //수정이 일어난 템픒릿ㅇ른 이미 사용중이지 않기에 삭제 가능 ㅇㅇ 맞음.
            for(TemplateItemRegisterRequestDTO itemRequestDTO : templateRegisterRequestDTO.getItem()){
                Optional<Item> selectedItem = itemRepository.findById(itemRequestDTO.getItemId());
                if (selectedItem.isPresent()) {
                    Item item = selectedItem.get();
                    // 템플릿에 아이템 추가
                    if (!template.getTemplateItem().contains(item)) {
                        template.getTemplateItem().add(item); // 템플릿에 아이템 추가
                        item.getTemplates().add(template);    // 아이템에 템플릿 추가
                    }
                    // 아이템 저장 (아이템에서 템플릿 정보까지 반영)
                    itemRepository.save(item);
                } else {
                    // 아이템이 존재하지 않으면 예외 처리 (선택사항)
                    throw new ItemOptionIsNullException();
                }
            }
            templateRepository.save(template);
        } else {
            throw new TemplateUseInContentException();
        }
    }

    //템플릿 소프트 삭제 이후 중간테이블에는 해당 데이터가 신규 생길 수 없음. 기존 사용중인 컨텐츠에서는 사용해야하기에 유지
    public void softDeleteTemplate(TemplateDeleteRequsetDTO templateDeleteRequsetDTO) {
        Optional<Template> template = templateRepository.findById(templateDeleteRequsetDTO.getId());
        if (template.isPresent() && template.get().getProvider().equals(templateDeleteRequsetDTO.getProvider())) {
            Template deleteTemplate = template.get();
            deleteTemplate.setIsDeleted(true);
            templateRepository.save(deleteTemplate);
        } else {
            //아이템이 없거나 자신의아이템이 아님을 알려주는 익셉션
            log.error("등록된 Template 존재하지 않습니다.: {}", templateDeleteRequsetDTO.getId());
            throw new TemplateNotRegisterFromUserException();
        }
    }
}
