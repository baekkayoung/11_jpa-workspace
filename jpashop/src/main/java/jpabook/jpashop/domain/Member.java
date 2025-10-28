package jpabook.jpashop.domain;

import jakarta.persistence.*;
import org.hibernate.Length;
import org.hibernate.annotations.CollectionId;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {


//    @GeneratedValue(strategy = GenerationType.AUTO) 기본 auto
    @Id
    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    private String name;
    private String city;
    private String street;
    private String zipcode;

    @OneToMany(mappedBy = "member") // 객체명 member로 연결
    private List<Order> orders = new ArrayList<>(); // new~ 여기는 관례적으로

    public Member(){}

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
