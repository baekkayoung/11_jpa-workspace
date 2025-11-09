package restaurant.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import restaurant.reservation.domain.menu.Menu;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class OrderMenu {

    @Id @GeneratedValue
    @Column(name = "ORDER_MENU_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="ORDER_ID")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="MENU_ID")
    private Menu menu;

    @Column(name="ORDER_PRICE")
    private int orderPrice;

    private int count;

    // 연관관계 메소드
    public void setOrder(Order order) {
        this.order = order;
        order.getOrderMenus().add(this);
    }

    // 생성메서드

    public static OrderMenu createOrderMenu(Menu menu, int orderPrice, int count) {
        OrderMenu orderMenu = new OrderMenu();
        orderMenu.setMenu(menu);
        orderMenu.setOrderPrice(orderPrice);
        orderMenu.setCount(count);
        return orderMenu;
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }


}
