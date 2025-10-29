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
