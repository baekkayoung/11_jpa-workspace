package jpabook.jpashop.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        // code
        try{
//            Child child1 = new Child();
//            Child child2 = new Child();
//
//            Parent parent = new Parent();
//            parent.addChild(child1);
//            parent.addChild(child2);
//
//            em.persist(parent);
////            em.persist(child1);
////            em.persist(child2);

        Book book = new Book();
        book.setName("JPA의 정석");
        book.setAuthor("차은우");

        em.persist(book);

        em.flush();;
        em.clear();

        String query = "select i from Item i where type(i) = Book";

            List<Item> result = em.createQuery(query, Item.class).getResultList();

            for (Item item : result) {
                System.out.println("BOOK 아이템 : "+item.getName());
            }


            tx.commit();


        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
