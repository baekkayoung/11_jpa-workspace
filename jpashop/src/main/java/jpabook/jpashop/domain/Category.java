package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Category extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent; // 이 카테고리의 상위 카테고리 => 카테고리의 상위 => 다

    @OneToMany(mappedBy = "parent") // 내가 자식이다 = OneToMany
    private List<Category> child = new ArrayList<>(); // 하위 카테고리


}
