package hellojpa;

import jakarta.persistence.*;
import jdk.jfr.Name;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue @Column(name = "TEAM_ID")
    private Long id;

    private String name;


    // mappedBy는 조회만 가능
    // 가짜매핑임 매핑이된거ㅓㅊ럼 보이지만 실질적으로 뭔가가 되는 건 아님

    // 일대다 단방향은 일이 연관관계의 주인
    @OneToMany // team 이라고 하는 필드랑 연결.
    @JoinColumn(name = "TEAM_ID") // 얘를 안하면 이상한 중간테이블이 생김
    private List<Member> members = new ArrayList<>();

//    public void addMember(Member member) {
//        member.setTeam(this);
//        members.add(member);
//    }


    public Team(){}

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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
