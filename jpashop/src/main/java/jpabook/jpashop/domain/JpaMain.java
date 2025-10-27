package jpabook.jpashop.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        // code
        try{
            
            // 슈더 코드
            Order order = em.find(Order.class, 1L); // 주문을 찾아서
            Long memberId = order.getMemberId(); // 주문의 member의 id만 찾은 거임 객체 찾은 거 x
            Member member = em.find(Member.class, memberId);

            Member findMember = order.getMember(); // 이게 더 객체 지향적
            // 객체는 참조를 찾아갈 수 있어야



            tx.commit();



        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
