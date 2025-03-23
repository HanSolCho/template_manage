package com.onj.template_manage.repository;

import com.onj.template_manage.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateRepositoryCustomImpl implements TemplateRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public TemplateRepositoryCustomImpl(EntityManager entityManager) {
//        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Template> findTemplateByFilters(String name, String type, String provider, AccessLevel accessLevel, Pageable pageable) {
//        QItem qItem = QItem.item;
        QTemplate qTemplate = QTemplate.template;

        BooleanExpression predicate = buildPredicate(name, type, provider, accessLevel, qTemplate);

        // QueryDSL 쿼리 작성
        var query = queryFactory
                .selectFrom(qTemplate)
                .where(predicate);

        // 페이징 처리
        long total = query.fetchCount();
        var resultList = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(resultList, pageable, total);
    }

    private BooleanExpression buildPredicate(String name, String type, String provider, AccessLevel accessLevel, QTemplate qTemplate) {
        BooleanExpression predicate = qTemplate.isDeleted.isFalse();

        if (name != null && !name.isEmpty()) {
            predicate = predicate.and(qTemplate.name.containsIgnoreCase(name));
        }

        if (type != null) {
            predicate = predicate.and(qTemplate.type.eq(type));
        }

        if (provider != null && !provider.isEmpty()) {
            // provider가 일치하는 경우는 PRIVATE과 PUBLIC을 모두 포함
            predicate = predicate.and(
                    qTemplate.provider.eq(provider)
                            .or(qTemplate.accessLevel.eq(AccessLevel.PUBLIC))
            );
        } else {
            // provider가 없으면 PUBLIC 템플릿만 포함
            predicate = predicate.and(qTemplate.accessLevel.eq(AccessLevel.PUBLIC));
        }

        return predicate;
    }
}
