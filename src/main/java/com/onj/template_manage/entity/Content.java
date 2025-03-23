package com.onj.template_manage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 콘텐츠명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private Template template; // 콘텐츠가 사용한 템플릿

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentItemData> itemDataList = new ArrayList<>(); // 콘텐츠에 포함된 아이템 데이터

    @Column(nullable = false)
    private Date date; // 생성일자

    @Column(nullable = false)
    private String provider; // 콘텐츠를 생성한 사용자

    public void addItemData(ContentItemData contentItemData) {
        itemDataList.add(contentItemData);
        contentItemData.setContent(this);
    }

    public void removeItemData(ContentItemData contentItemData) {
        itemDataList.remove(contentItemData);
        contentItemData.setContent(null);
    }
}
