package jpabook.jpashop1.api;

import jpabook.jpashop1.domain.Address;
import jpabook.jpashop1.domain.Order;
import jpabook.jpashop1.domain.OrderStatus;
import jpabook.jpashop1.repository.OrderRepository;
import jpabook.jpashop1.repository.OrderSearch;
import jpabook.jpashop1.repository.order.simple.OrderSimpleQueryDto;
import jpabook.jpashop1.repository.order.simple.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/*
* xToOne(ManyToOne, OneToMany)
* Order 누구꺼냐 왜안오냐
*
* */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    // v1
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }

    // v1.1
    @GetMapping("/api/v1.1/simple-orders")
    public List<Order> ordersV1_1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch()); // 오더서치 뭐지?

        for (Order order : all) {
            order.getMember().getName(); // 이때 객체 초기화. 멤버가 필요한 시점
            order.getDelivery().getAddress(); // 객체 초기화

        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){ // Order 엔티티로 반환형 쓰지마!!

        // N + 1 < - > 1 + N
        // Member N번 + Delivery N번
        List<Order> orders = orderRepository.findAllByString(new OrderSearch()); // 이때는 오더만 가지고 옴
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        // 건바이 건으로 들어감 멤버다 그런거에 대한 정보는 없음. 레이지라.
        return  result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order o) {
            orderId = o.getId(); // 진짜 객체로.
            name = o.getMember().getName(); // 이때 초기화
            orderDate = o.getOrderDate();
            orderStatus = o.getStatus();
            address = o.getDelivery().getAddress();
        }
    }
}