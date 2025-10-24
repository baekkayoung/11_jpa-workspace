package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity// 이거를 꼭 붙여야 관리해줌
//@Table(name = "TB_MEMBER"); 테이블명이랑 VO ClassName 다를 시 명시
public class Member {

// 하나하나를 entity라고 부름
    
    @Id // PK라고 인식! 잘못연결하면 큰일 남
    private Long id;
//    @Column(name="usename") db cloumn이랑 vo명 다를 시
    private String name;

    public Member (){}

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
