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
    private int price;

    private int count;
}
