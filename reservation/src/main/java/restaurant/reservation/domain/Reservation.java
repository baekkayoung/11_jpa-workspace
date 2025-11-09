package restaurant.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    // 연관 관계 메소드
    public void setCustomer(Customer customer){
        this.customer =customer;
        customer.getReservations().add(this);
    }
    public void setTables(Tables tables){
        this.tables = tables;
        tables.getReservations().add(this);
    }

    public void setOrder(Order order){
        if (this.status != ReservationStatus.RESERVED) {
            throw new IllegalStateException("예약확정 상태(RESERVED)인 경우에만 주문할 수 있습니다.");
        }
        this.order = order;
        order.getReservations().add(this);
    }


    // 예약 생성 메소드
    public static Reservation createReservation(Customer customer, Tables tables, Order order,
                                                int reservationPeopleCount, LocalDateTime time) {

        // 테이블 수용 인원 확인
        if (reservationPeopleCount > tables.getCapacity()) {
            throw new IllegalArgumentException("테이블 수용 인원을 초과했습니다.");
        }

        // 테이블 예약 가능 확인
        if (tables.getStatus() != TableStatus.AVAILABLE) {
            throw new IllegalStateException("현재 테이블은 예약이 불가능한 상태입니다.");
        }

        // 예약 생성
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setTables(tables);
        reservation.setOrder(order);
        reservation.setReservationPeopleCount(reservationPeopleCount);
        reservation.setReservationTime(time);
        reservation.setStatus(ReservationStatus.RESERVED);

        // 테이블 상태 변경
        tables.setStatus(TableStatus.RESERVED);
        return reservation;
    }

    // 예약 취소 메서드
    public void cancel() {
        if (this.status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("이미 취소된 예약입니다.");
        }
        this.setStatus(ReservationStatus.CANCELLED);
        this.getTables().setStatus(TableStatus.AVAILABLE);
    }

}
