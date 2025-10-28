package hellojpa;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Member {

    @Id
    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT") // 멤버와 프로덕트를 연결하는 중간 테이블 생성 각각 id(pk)를 가지고 만듦
    private List<Product> products = new ArrayList<>();


    // @Column(name="TEAM_ID")
    // private Long teamId;

    // id만 주는 게 아니라 자체를
    // JPA에게 관계를 알려줘야 함! 누가 다(N) 고 누가 일(1) 인지
    // db랑도 관계를 엮어줘야 => 어느 컬럼이랑?

//    @ManyToOne // 다대일
//    @ManyToOne(fetch = FetchType.LAZY) //
//    @JoinColumn(name = "TEAM_ID") // DB 보면 FK-PK 연결 되어있음
//    private Team team;

    public Member(){}

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public Team getTeam() {
//        return team;
//    }
//
//    public void changeTeam(Team team) { //setTeam => changeTeam
//        this.team = team;
//        team.getMembers().add(this);
//        // team.getMembers().add(member) 한거랑 같음.
//        // 연관관계 편의 메소드
//    }
//
//    public void setTeam(Team team) {
//        this.team = team;
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}