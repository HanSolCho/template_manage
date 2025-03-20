package com.onj.template_manage.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "item_option")
public class ItemOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String optionValue; // 옵션 값 (예: '옵션1', '옵션2' 등)

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item; // 연관된 아이템

}