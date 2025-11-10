package jpabook.jpashop1.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter // 임베디드 값타입은 엔티티간의 공유를 하기때문에 Setter를 하게 되면 값이 서로서로 바뀌기 때문에 Setter는 제외
public class Address {
//    public Address(){} 이거나
    protected Address(){}


    // 객체 생성 시 초기값 설정
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    private String city;
    private String street;

    private String zipcode;
}
