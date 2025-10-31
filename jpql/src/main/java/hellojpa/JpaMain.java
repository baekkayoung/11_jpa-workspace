package hellojpa;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Objects;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // code
        try {

            /*
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Address address = new Address("서울", "테헤란로", "10000");


            Member member = new Member();
            member.setUsername("멤버");
            member.setTeam(team);
            member.setAddress(address);
            member.setAge(10);
            member.setMemberType(MemberType.ADMIN);
            em.persist(member);


            Member member2 = new Member();
            member2.setUsername("관리자");
            member2.setAddress(address);
            member2.setAge(30);
            member2.setMemberType(MemberType.USER);
            em.persist(member2);*/

            Product product1 = new Product();
            product1.setName("Apple");
            product1.setPrice(4000);
            product1.setStockAmount(2);
            em.persist(product1);

            Product product2 = new Product();
            product2.setName("lipstick");
            product2.setPrice(100);
            product2.setStockAmount(1);
            em.persist(product2);

            Address address1 = new Address("서울", "테헤란로", "10000");
            Address address2 = new Address("인천", "선학로", "21910");

            Order order1 = new Order();
            order1.setAddress(address1);
            order1.setOrderAmount(0);
            order1.setProduct(product1);
            em.persist(order1);

            Order order2 = new Order();
            order2.setAddress(address2);
            order2.setOrderAmount(0);
            order2.setProduct(product2);
            em.persist(order2);

            em.flush();
            em.clear();



            // 18.
            String query = "select o from Order o join o.product p" +
                    " where p.id = Any(select o1.product.id from Order o1)";

            List<Order> resultList = em.createQuery(query,Order.class).getResultList();

            for (Order order : resultList) {
                System.out.println(order);
            }

//            String query = "select o from Order o"
//                    + " where o.product = ANY(select p from Product p)";
//            List<Order> orders = em.createQuery(query, Order.class).getResultList();
//            for (Order o1 : orders) {
//                System.out.println("그 상품을 주문한 주문 : " + o1);
//            }



//            String query = "select m from Member m where m.memberType = hellojpa.jpql.MemberType.ADMIN";
//            List<Member> resultList = em.createQuery(query, Member.class).getResultList();
//
//            for (Member member1 : resultList) {
//                System.out.println("Admin인 회원 : " + member1);
//            }



        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            em.close();
        }
        emf.close();
    }


}
