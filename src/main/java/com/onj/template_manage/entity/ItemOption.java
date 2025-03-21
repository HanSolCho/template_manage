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
    private String optionValue;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

}