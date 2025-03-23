package com.onj.template_manage.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "template")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // 템플릿명

    @Column(nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessLevel accessLevel;  // 공개범위 (ENUM: public, private)

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private Date date;

    // Many-to-Many 관계 설정
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "template_item",  // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "template_id"),  // 템플릿 측 외래키
            inverseJoinColumns = @JoinColumn(name = "item_id")  // 아이템 측 외래키
    )
    private List<Item> templateItem = new ArrayList<>();  // 템플릿에 포함된 아이템들

    @Column(nullable = false)
    private Boolean isDeleted = false;  // 소프트 삭제 필드
}