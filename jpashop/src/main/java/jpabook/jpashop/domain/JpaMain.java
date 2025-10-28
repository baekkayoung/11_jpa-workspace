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
            
            Order order = new Order();
//            order.addOrderItem();  => 이렇게 못 함
//            order.addOrderItem(new OrderItem()); // => 알트엔터로 메소드 만들기
            em.persist(order); // 주문

            OrderItem orderItem =new OrderItem();
            orderItem.setOrder(order);
            em.persist(orderItem);

            tx.commit();



        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
