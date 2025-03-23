package com.onj.template_manage.repository;

import com.onj.template_manage.entity.ContentItemData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentItemDataRepository extends JpaRepository<ContentItemData, Long> {
}

