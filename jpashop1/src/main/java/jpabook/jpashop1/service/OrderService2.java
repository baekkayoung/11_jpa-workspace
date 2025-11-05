package jpabook.jpashop1.service;

import jpabook.jpashop1.domain.*;
import jpabook.jpashop1.domain.Item.Item;
import jpabook.jpashop1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService2 {
    private final MemberRepository2 memberRepository2;
    private final OrderRepository2 orderRepository2;
    private final ItemRepository2 itemRepository2;


    /*
     * 주문
     * @param memberId
     * @param itemId
     * @param count
     * @return
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        // 엔티티 조회
        Member member = memberRepository2.findOne(memberId);
        Item item = itemRepository2.findOne(itemId);

        // 배송정보 생성-> 얘는 시간상 생짜로
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository2.save(order);
        return  order.getId();
    }
    
    /* 주문 취소*/
    @Transactional
    public void cancelOrder(Long orderId){

        // 주문 엔티티 조회
        Order order = orderRepository2.findOne(orderId);
        // 주문 취소
        order.cancel();
    }
}
