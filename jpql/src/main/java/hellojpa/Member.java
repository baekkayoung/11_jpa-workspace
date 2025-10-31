package hellojpa;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity(name = "Member")
public class Member {
    @Id @GeneratedValue

    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = LAZY) // 기본값은 즉시로딩이라 바꿔주기
    @JoinColumn(name="TEAM_ID") // 주인은 조인컬럼
    private Team team;

    @Embedded
    private Address address;

    @Enumerated(EnumType.ORDINAL)
    private MemberType memberType;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", address=" + address +
                '}';
    }
}
