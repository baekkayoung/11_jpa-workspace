package jpabook.jpashop1.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    // 핏하게 들어가는 것

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos(){
        List<OrderQueryDto> result = findOrders(); // 멤버랑 딜리버리 조인해서 가지고 온 것. 일대 다인 오더아이템즈빼고 조회해옴
// 5개
        
        result.forEach(o->{ // result에는 주문 두건이을 싹 가지고 온 상태고 for Each로 주문 한건한건 얘가 주문한 거 찾아줘!
                List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId()); // 1번이 주문한 상품은 뭐야? 2번이 주문한 상품이 뭐야?
                o.setOrderItems(orderItems);
        }); // 마지막 1개

        return result; // 6개
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
         return  em.createQuery(
                 "select new jpabook.jpashop1.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                         " from OrderItem oi " +
                         " join oi.item i" +
                         " where oi.order.id = :orderId", OrderItemQueryDto.class)
                 .setParameter("orderId", orderId)
                 .getResultList();
                 // 주문은 이미 findOrders로 찾았음 근데 뭘 산거냐?
                 // 주문상품 테이블의 주문과 상품 각각의 아이디를 알 수 있음. 둘을 조인해서 웨어절로 저렇게 함

    }


    private List<OrderQueryDto> findOrders(){ // private public 상관없는데 여기서 쓸거니까 ㄱㄱ
        return em.createQuery(
                "select new jpabook.jpashop1.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o"
                        + " join o.member m"
                        + " join o.delivery d", OrderQueryDto.class
        ).getResultList(); // 멤버와 배송지만. 오더는 모름
    }



}
