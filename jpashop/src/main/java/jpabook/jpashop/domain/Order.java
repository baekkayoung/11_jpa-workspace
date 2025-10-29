package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue // 안 적으면 auto
    @Column(name = "ORDER_ID")
    private Long id;

//    @Column(name="MEMBER_ID")
//    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;
    // id만 가지고 오는 게 아니라 박선일이라는 사람 자체를 가지고 옴
    // 회원 객체 자체를 들고 있음

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    private LocalDateTime orderDate; // 시간까지 ORDER_DATE order_date

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void addOrderItem(OrderItem orderItem) {

        // 순수한 객체 상태로 맞추기 위해
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        // 연관 메소드는 게스트 주인 어디에서 만드는건지는 선택
        // 이거는 게스트에서 만든거임..

    }

    public  Order(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


}
