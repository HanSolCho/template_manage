package com.onj.template_manage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;

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
    private String optionValue;
    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

}