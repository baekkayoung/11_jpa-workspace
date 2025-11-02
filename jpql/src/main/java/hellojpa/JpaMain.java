package hellojpa;

import hellojpa.dto.ProductDTO;
import jakarta.persistence.*;
import hellojpa.dto.OrderProductDTO;

import java.util.List;

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
            product1.setPrice(100);
            product1.setStockAmount(3);
            em.persist(product1);

            Product product2 = new Product();
            product2.setName("melon");
            product2.setPrice(2000);
            product2.setStockAmount(3);
            em.persist(product2);

            Product product3 = new Product();
            product3.setName("테스트 상품");
            product3.setPrice(6000);
            product3.setStockAmount(3);
            em.persist(product3);

            Address address1 = new Address("서울", "테헤란로", "10000");
            Address address2 = new Address("인천", "선학로", "21910");

            Order order1 = new Order();
            order1.setAddress(address1);
            order1.setOrderAmount(1);
            order1.setProduct(product1);
            em.persist(order1);

            Order order2 = new Order();
            order2.setAddress(address2);
            order2.setOrderAmount(2);
            order2.setProduct(product2);
            em.persist(order2);

            Order order3 = new Order();
            order3.setAddress(address2);
            order3.setOrderAmount(3);
            order3.setProduct(product3);
            em.persist(order3);

            em.flush();
            em.clear();

            String query = "select new hellojpa.dto.OrderProductDTO(o.orderAmount, name) " +
                    "from Order o join o.product p";

            List<OrderProductDTO> resultList = em.createQuery(query, OrderProductDTO.class).getResultList();

            for (OrderProductDTO orderProductDTO : resultList) {
                System.out.println("주문의 금액 : " + orderProductDTO.getOrderAmount() + " 주문한 상품 이름 : "+ orderProductDTO.getProductName());
            }


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
