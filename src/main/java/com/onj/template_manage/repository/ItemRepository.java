package com.onj.template_manage.repository;

import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemType;
import com.onj.template_manage.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository  extends JpaRepository<Item, Long>, ItemRepositoryCustom, QuerydslPredicateExecutor<Item> {

}
