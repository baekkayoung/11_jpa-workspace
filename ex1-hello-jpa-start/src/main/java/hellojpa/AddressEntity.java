package hellojpa;

import jakarta.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id @GeneratedValue
    private Long id;
    
//    @Embedded 생략하더라도 반대 인버스에 있어서 ㄱㅊ
    private Address address; // 임베디드로 만들어뒀기때문에

    public AddressEntity() {} // 기본

    /* 시틱값, 스트릿, 집코드 주는 상황. 이거를 받아주려면*/
    public AddressEntity(String city, String street, String zipcode) { // 매개변수 생성자
        this.address = new Address(city, street, zipcode);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
