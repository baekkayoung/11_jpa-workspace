package hellojpa;

import jakarta.persistence.*;
import org.hibernate.internal.build.AllowSysOut;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        // 공장 하나만 세움
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 요청이 있을 때마다 만든다. => 사용하고 버려야 함
        EntityManager em = emf.createEntityManager();

        // 모든 데이터를 변경하는 작업은 트랜잭션 안에 해야 함
        // 트랜잭션 생성
        EntityTransaction tx = em.getTransaction();
        // 트랜잭션 생성
        tx.begin();

        // code
        try{

            Member member = new Member();
            member.setUsername("member1");

            em.persist(member); // TEAM_ID :  null로 들어가

            Team team = new Team();
            team.setName("teamA"); // member에 업데이트가 감.. 약간 이상 그래서 일대다 안 씀
            team.getMembers().add(member); // 가짜 매핑일때는 안됨 근데 일대다 단방향은 1이 주인이니까 됨
            // Team에서 Member를 추가 (하지만 Member의 TEAM_ID는 아직 null)
            // team 이 주인이잖아
            // 이 부분이.. 멤버의 팀을 바꾸는건데? 그래서 update가 한 번 더 나가는 거임

            /*
            *
            *  update team을 set 했는데 왜 member가 update
        Member
    set
        TEAM_ID=?
    where
        MEMBER_ID=?
            *
            *
            * */

            // JPA가 flush 할 때
// "어? Team의 members에 Member가 있네. 근데 외래키 TEAM_ID는 아직 안 들어갔네"
// → 그래서 update로 외래키를 수정함
// => UPDATE member set TEAM_ID = ? where MEMBER_ID = ?

            em.persist(team);
            tx.commit();



        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
            emf.close();





    }
}
