package com.onj.template_manage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "item") //아이템 : 아이템 명, 아이템타입(text,checkbox,dropdown), 생성자, 사용여부
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private ItemType type;
    @Column(nullable = false)
    private String provider;
    @Column(nullable = false)
    private Boolean isDeleted;
    @Column(nullable = false)
    private Date date;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOption> itemOptions; // 아이템이 checkbox나 dropdown 타입일 경우 선택할 옵션들
}
