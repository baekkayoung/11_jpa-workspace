package jpabook.jpashop1.domain.Item;

import jakarta.persistence.*;
import jpabook.jpashop1.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private int price;
    private int stockQuantity; // 재고 수량

    @ManyToMany(mappedBy = "items")
//    @JoinTable(name="category_item") 이미 category에서 적어뒀기 때문에 또 적지 않아도 됨
    private List<Category> category = new ArrayList<>();

}
