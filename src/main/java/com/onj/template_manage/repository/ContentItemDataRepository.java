package com.onj.template_manage.repository;

import com.onj.template_manage.entity.ContentItemData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentItemDataRepository extends JpaRepository<ContentItemData, Long> {
    ContentItemData findByContentIdAndItemId(Long contentId, Long itemId);
//    List<ContentItemData> findByContentIdAndItemId(Long contentId, Long itemId);
}

