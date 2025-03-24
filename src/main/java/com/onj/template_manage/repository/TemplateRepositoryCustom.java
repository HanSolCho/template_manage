package com.onj.template_manage.repository;

import com.onj.template_manage.entity.AccessLevel;
import com.onj.template_manage.entity.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TemplateRepositoryCustom {
    Page<Template> findTemplateByFilters(String name, String type, String provider, AccessLevel accessLevel, Pageable pageable);
}
