package jpabook.jpashop1.api;

import jpabook.jpashop1.domain.Address;
import jpabook.jpashop1.domain.Order;
import jpabook.jpashop1.domain.OrderItem;
import jpabook.jpashop1.domain.OrderStatus;
import jpabook.jpashop1.repository.OrderRepository;
import jpabook.jpashop1.repository.OrderSearch;
import jpabook.jpashop1.repository.order.query.OrderQueryDto;
import jpabook.jpashop1.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository  orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for (Order order : all) {
            order.getMember().getName(); // Lazy 강제 초기화, 엔티티 자체만 조회하는 건 쿼리가 안 나감!
            order.getDelivery().getAddress(); // Lazy 강제 초기화, 엔티티의 필드를(db상의 컬럼)을 호출할 때
            List<OrderItem> orderItems = order.getOrderItems(); // orderItems 가지고 옴 but 엔티티만 조회한 것이기 때문에 엔티티 조회 안됨
            orderItems.stream().forEach(o->o.getItem().getName()); // 주문 한건 한건이 o에 들어가는데, orderItems의 item에 getName으로 접근! 이때 강제 초기화.
        }

        return all;
    }


    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> result = orders.stream().
                map(o -> new OrderDto(o))
                        .collect(toList());
        return result;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> orders3(){
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orders4(
            @RequestParam(value = "offset", defaultValue = "0")int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit); // 얘네는 그냥 페치조인 됨
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }


    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        return orderQueryRepository.findOrderQueryDtos();
    }



    @Data
    static class OrderDto{
        private Long orderId;
        private String name; //  주문자 이름
        private LocalDateTime orderDate; // 주문 시간
        private OrderStatus orderStatus; // 주문 상태
        private Address address; // 배송 장소
        private List<OrderItemDto> orderItems; // 산 물건들()

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // lazy 강제 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // Lazy 강제 초기화
            //order.getOrderItems().stream().forEach(o->o.getItem().getName()); // 첫번째산 아이템의 이름은 뭐냐.... Lazy 강제 초기화가 일어남
            //orderItems = order.getOrderItems(); // 드디어 됨. 앞에서 Lazy 강제 초기화 했으니까.

//            orderItems = order.getOrderItems(); 엔티티 그 자체라 안됨.. 기본 : Lazy 원투매니
//            orderItems = order.getOrderItems().stream().map(orderItems->new OrderItemDto()).

            orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem)) // 오더아이템 객체가 담심
                    .collect(toList());

        }

        @Data
        static class OrderItemDto{
            private String itemName;
            private int orderPrice;
            private int count;

            public OrderItemDto(OrderItem orderItem) {
                itemName = orderItem.getItem().getName(); // Lazy 강제 초기화
                orderPrice = orderItem.getOrderPrice(); // 바로 가지고 올 수 있음 초기화랑 관련 없음
                count = orderItem.getCount();
            }

        }
    }
}
