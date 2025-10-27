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


            Member member1 =new Member();
            member1.setUsername("김수정");

            Member member2 =new Member();
            member2.setUsername("김수정");

            Member member3 =new Member();
            member3.setUsername("김수정");
            // 비영속 상태

            System.out.println("========================");

            em.persist(member1); // 영속
            em.persist(member2); //
            em.persist(member3); //

            // select 성능 저하  => db로부터 allocationSize50개를 떙겨오자
            // 1번부터 50번까지를 쓰는거임 => 처음에만 select 2,3은 안함

            System.out.println("member1: " +member1.getId());
            System.out.println("member2: " +member2.getId());
            System.out.println("member3: " +member3.getId());


            tx.commit();



        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
            emf.close();





    }
}
