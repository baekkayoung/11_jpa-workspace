package jpabook.jpashop1.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop1.domain.Address;
import jpabook.jpashop1.domain.Item.Book;
import jpabook.jpashop1.domain.Item.Item;
import jpabook.jpashop1.domain.Member;
import jpabook.jpashop1.domain.Order;
import jpabook.jpashop1.domain.OrderStatus;
import jpabook.jpashop1.exception.NotEnoughStockException;
import jpabook.jpashop1.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    
    
    @Test
    public void 상품주문() throws Exception {

        Member member = createMember();

        Book book = createBook("어린왕자", 10000, 10);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        // 기댓값이랑 실제값이랑 같은지 확인 -> 맞으면 초록불
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(),"상품 주문시 상태는 ORDER");

        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품의 종류 수가 정확해야 한다.");

        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }


    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("어린왕자", 10000, 10);

        int orderCount = 10;

        //when
        /*
        try {
            Long orderId = orderService.order(member.getId(), item.getId(), orderCount);// 1차 캐시
        }catch(NotEnoughStockException e){
            return;
        }*/

        assertThrows(NotEnoughStockException.class,
                ()-> orderService.order(member.getId(), item.getId(), orderCount));

    }
    
    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA",10000,10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(),"주문 취소시 상태는 CANCEL 이다");
        assertEquals(10, item.getStockQuantity(),"주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }

    // Ctrl + alt + p -> 파라미터 자동 추출
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        //given
        Member member = new Member();
        member.setName("회원");
        member.setAddress(new Address("서울", "테헤란로","10000"));
        em.persist(member);
        return member;
    }

    

}