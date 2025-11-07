package restaurant.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.lang.model.element.Name;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name="RESERVATION_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="CUSTOMER_ID")
    private Customer customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TABLE_ID")
    private Tables tables;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="ORDER_ID")
    private Order order;

    @Enumerated
    private ReservationStatus status;

    private int reservationPeopleCount;

    private LocalDateTime reservationTime;

}
