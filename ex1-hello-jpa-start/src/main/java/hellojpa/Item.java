package hellojpa;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS )
@DiscriminatorColumn
public abstract class Item { // 추상클래스로 만들어둬야 구현클래스 테이블 전략으로 생성시 아이템 테이블 생성 안됨
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
