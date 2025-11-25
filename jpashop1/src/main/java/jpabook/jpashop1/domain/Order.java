package jpabook.jpashop1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    // private Date orderDate;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    //=============================================//

    protected Order(){}; // Order order = new Order(); => service에서 이거 안 됨

    // === 연관 관계 편의 메소드 ===
    public void setMember(Member member){
        this.member = member; // 위 = 매개 order.setMember(Member);
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // == 생성 메소드 == //

    /*(누가 샀는지, 배송정보, 뭐샀는지 : 가변인자문법!)*/
    /*
     가변인자문법 OrderItem... orderItems => OrderItem[] orderItems
     createOrder(m, d, orderItem1);
     createOrder(m, d, orderItem1, orderItem2); []로 하면 안됨 : 배열로 온 게 아니니까.. 하나를 줘도 되고 두 개를 줘도 되고
     createOrder(m, d, orderItem[0]); : OrderItem[] orderItems
     */
    
    public static Order createOrder(Member member, Delivery delivery,
                                    OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member); // 누가 샀어? : 연관관계 편의 메소드임 setter 아님
        order.setDelivery(delivery); // 연관관계 편의 메소드임! setter 아님
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem); // orderItems에 orderItem 추가 하는 연관관계메소드
            // 그럼 얘는 orderItem.setOrder(this); 얘는 안되는건가? 아니면 저거 하나로 연결하면서 되는건가
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // === 비즈니스 로직 ===//
    /* 주문 취소 */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        // 재고 원복
        for (OrderItem orderItem : orderItems) { 
            orderItem.cancel();
        }
    }

    // == 조회 로직 == //
    /* 전체 주문 가격 조회 */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }



}
