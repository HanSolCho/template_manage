package com.onj.template_manage.repository;

import com.onj.template_manage.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Optional<Content>> findByTemplateId(Long templateId);
}
