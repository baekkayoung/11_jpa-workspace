package jpabook.jpashop1.repository;

import jpabook.jpashop1.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName; // 회원명
    private OrderStatus orderStatus; // 주문상태 [ORDER, CANCEL]'

}
