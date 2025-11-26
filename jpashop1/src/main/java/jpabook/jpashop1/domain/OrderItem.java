package jpabook.jpashop1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpabook.jpashop1.domain.Item.Item;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

@Entity
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    protected OrderItem(){} // 기본 생성자로 생성하는 것 제한

    // == 생성 메서드 == //
    public static  OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 재고를 줄여줘야함!
        item.removeStock(count);
        return orderItem;
    }


    // == 비즈니스 로직 == //
    public void cancel() {
        // 아이템을 가지고 온다. 재고를 늘린다.
        getItem().addStock(count);
    }

    // == 조회 로직 == //
    /** 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return getOrderPrice() * getCount(); // count 말고 ... 가지고 오려면 getter를 호출해야 함
    }

}
