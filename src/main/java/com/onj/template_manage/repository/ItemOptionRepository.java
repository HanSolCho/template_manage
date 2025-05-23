package com.onj.template_manage.repository;

import com.onj.template_manage.entity.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOptionRepository  extends JpaRepository<ItemOption, Long> {
    List<ItemOption> findByItemId(Long id);
}
