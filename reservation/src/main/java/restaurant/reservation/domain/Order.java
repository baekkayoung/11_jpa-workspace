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
}
