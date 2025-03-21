package com.onj.template_manage.repository;

import com.onj.template_manage.entity.Item;
import com.onj.template_manage.entity.ItemType;
import com.onj.template_manage.entity.QItem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ItemRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Item> findItemsByFilters(String name, ItemType type, String userId, Pageable pageable) {
        QItem qItem = QItem.item;

        BooleanExpression predicate = buildPredicate(name, type, userId, qItem);

        // QueryDSL 쿼리 작성
        var query = queryFactory
                .selectFrom(qItem)
                .where(predicate);

        // 페이징 처리
        long total = query.fetchCount();
        var resultList = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(resultList, pageable, total);
    }

    private BooleanExpression buildPredicate(String name, ItemType type, String provider, QItem qItem) {
        BooleanExpression predicate = qItem.isDeleted.isFalse(); // 기본적으로 삭제되지 않은 아이템만 조회

        if (name != null && !name.isEmpty()) {
            predicate = predicate.and(qItem.name.containsIgnoreCase(name));
        }

        if (type != null) {
            predicate = predicate.and(qItem.type.eq(type));
        }

        if (provider != null && !provider.isEmpty()) {
            predicate = predicate.and(qItem.provider.eq(provider)); // User 엔티티와 연결된 부분
        }

        return predicate;
    }
}