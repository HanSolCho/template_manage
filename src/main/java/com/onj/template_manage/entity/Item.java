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
    private List<ItemOption> itemOptions;

    // Many-to-Many 관계 설정
    @ManyToMany(mappedBy = "templateItem")
    private List<Template> templates = new ArrayList<>();  // 여러 템플릿에 포함될 수 있는 아이템들


    public List<ItemOption> getItemOptions() {
        if (itemOptions == null) {
            return new ArrayList<>();  // null 대신 빈 리스트 반환
        }
        return itemOptions;
    }
}
