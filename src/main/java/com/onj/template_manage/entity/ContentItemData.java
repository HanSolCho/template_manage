package com.onj.template_manage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;

@Data
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "content_item_data")
public class ContentItemData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemValue; // 아이템 데이터 값 (예: 텍스트, 선택값 등)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item; // 아이템

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content; // 콘텐츠
}
