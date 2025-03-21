package com.onj.template_manage.repository;

import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long>,  TemplateRepositoryCustom, QuerydslPredicateExecutor<Template> {

}
