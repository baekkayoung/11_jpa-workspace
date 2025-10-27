package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue // 안 적으면 auto
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name="MEMBER_ID")
    private Long memberId;

    private Member member; // id만 가지고 오는 게 아니라 박선일이라는 사람 자체를 가지고 옴
    // 회원 객체 자체를 들고 있음


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    private LocalDateTime orderDate; // 시간까지 ORDER_DATE order_date

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public  Order(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
