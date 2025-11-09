package restaurant.reservation.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="ORDERS")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="ORDER_ID")
    private Long id;

    @OneToMany(mappedBy = "order" )
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 연관 관계 편의 메소드

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setOrder(this);
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenus.add(orderMenu);
        orderMenu.setOrder(this);
    }

    // 주문 생성 메소드
    public static Order createOrder(List<OrderMenu> orderMenus) {
        Order order = new Order();
        for (OrderMenu orderMenu : orderMenus) {
            order.addOrderMenu(orderMenu);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderTime(LocalDateTime.now());
        return order;
    }


    // 주문 취소 메소드
    public void cancel() {
        if (this.status == OrderStatus.CANCEL) {
            throw new IllegalStateException("이미 취소된 주문입니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
    }

    // 총 주문 금액 계산 메소드
    public int getTotalPrice() {
        return orderMenus.stream()
                .mapToInt(OrderMenu::getTotalPrice)
                .sum();
    }




}
