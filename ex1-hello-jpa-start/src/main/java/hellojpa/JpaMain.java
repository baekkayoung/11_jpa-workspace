package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.internal.build.AllowSysOut;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // code
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();

            List<Member> members = em.createQuery("select m from Member m join fetch m.team ", Member.class).getResultList();// 모든 멤버 결과 리스트로 줌



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace(); // 에러를 보려면 이거 있어야함 지금 에러 남
        } finally {
            em.close();
        }
        emf.close();
    }


}
