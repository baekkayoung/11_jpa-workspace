package jpabook.jpashop1;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop1.domain.*;
import jpabook.jpashop1.domain.Item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component // 컨트롤러, 서비스는 다 이걸 가지고 있음
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        // 스프링 빈이 생성되고 모든 의존성 주입이 완료된 직후 자동으로 실행되는 메서드에 붙이는 어노테이션
        // "이 객체가 준비 완료되면 딱 한 번 실행해줘!" 라는 의미
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component // 이렇게 해야 spring의 관리 대상으로 들어감
    @Transactional
    @RequiredArgsConstructor
    static class InitService { // 이런게 싫으면 외부에 만들어도 되지만 여기 파일에서만 사용하는거라 static으로 만듦

        private final EntityManager em;

        public void dbInit1(){
            System.out.println("=== 데이터 생성 시작 ===");
            // 아래를 드래그하고 메소드 추출인 컨+쉬+M 하면 회원 생성 메소드를 만들어줌
            //Member member = new Member();
            //member.setName("userA");
            //member.setAddress(new Address("서울", "1", "11111"));
            Member member = createMember("userA","서울","1","11111");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1); // 우리가 예전에 재고 줄이기까지 설정해둠 OrderItem 들어가면
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2(){
            System.out.println("=== 데이터 생성 시작 ===");

            Member member = createMember("userB","인천","2","22222");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3); // 우리가 예전에 재고 줄이기까지 설정해둠 OrderItem 들어가면
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }


        /*
            회원 생성 메소드
         */
        private static Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName("userA");
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        /*
            책 객체 생성 메소드
         */
        private Book createBook(String name, int price, int stockQuantity){
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        /*
            배송 정보 생성 메소드
         */
        private Delivery createDelivery(Member member){
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress()); // 말이 안되지만 수업 편의상 이렇게 만듦.
            return delivery;
        }

    }


}