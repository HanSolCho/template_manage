package com.onj.template_manage.repository;

import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> findItemsByFilters(String name, ItemType type, String provider, Pageable pageable);
}
