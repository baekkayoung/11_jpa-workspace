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

            Member member = em.find(Member.class,150L);
            member.setName("AAAAAAAA"); // update 쿼리 나가려다가

            em.clear(); // 영속성 컨텍스트가 empty 됨

            Member member2 = em.find(Member.class,150L); // 1차 캐시에서 가지오 올 게 없음. => select 쿼리 2번


            System.out.println("============= 구분선 =================");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
            emf.close();





    }
}
