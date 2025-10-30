package jpabook.jpashop.domain;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "parent" ,cascade= CascadeType.ALL )
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child){
        childList.add(child);
        child.setParent(this); // child 쪽에서 참조하고있는 parent..
        // this는 addChild()메소드를 호출한 객체 자신.. => this는 Parent객체
        // JPA에서 연관관계 주인(Child.parent) 쪽이 DB에 외래키(PARENT_ID)를 관리하기 때문에,이걸 안 하면 DB에 부모 정보가 안 들어감

        /*
        부모만 리스트에 추가하고 자식의 부모를 안 세팅하면 → DB에 외래키(PARENT_ID)가 null로 저장됨
        자식만 부모를 세팅하고 부모 리스트를 안 채우면 → 객체 그래프에서는 부모가 자식을 모름
        즉, 객체와 DB 모두에서 일관성을 유지하려면 양쪽 모두 세팅해야 함
        */
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

}
