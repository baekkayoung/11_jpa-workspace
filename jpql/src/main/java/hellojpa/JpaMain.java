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

//            Product product1 = new Product();
//            product1.setName("Apple");
//            product1.setPrice(100);
//            product1.setStockAmount(3);
//            em.persist(product1);
//
//            Product product2 = new Product();
//            product2.setName("melon");
//            product2.setPrice(2000);
//            product2.setStockAmount(3);
//            em.persist(product2);
//
//            Product product3 = new Product();
//            product3.setName("테스트 상품");
//            product3.setPrice(6000);
//            product3.setStockAmount(3);
//            em.persist(product3);
//
//            Address address1 = new Address("서울", "테헤란로", "10000");
//            Address address2 = new Address("인천", "선학로", "21910");
//
//            Order order1 = new Order();
//            order1.setAddress(address1);
//            order1.setOrderAmount(1);
//            order1.setProduct(product1);
//            em.persist(order1);
//
//            Order order2 = new Order();
//            order2.setAddress(address2);
//            order2.setOrderAmount(2);
//            order2.setProduct(product2);
//            em.persist(order2);
//
//            Order order3 = new Order();
//            order3.setAddress(address2);
//            order3.setOrderAmount(3);
//            order3.setProduct(product3);
//            em.persist(order3);

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "update Member m set m.age = 20";
            int resultCount = em.createQuery(query).executeUpdate(); // excuteupdate는 행수를 반환 -> 변수 int

            em.clear(); // 영속성 컨테스트 초기화.
            Member findMember = em.find(Member.class, member1.getId());// db에 잇는것 가지고 와서 영속 상태로 만들어 줌
            
            System.out.println("resultCount : "+ resultCount);

            System.out.println("member1.age  : " + findMember.getAge());
            // 영속성 컨테스트 안에서 가지고 오는 것 . -> 0살
            // 만약 clear로 비워주면 -> 영속성 컨테스트에 아무것도 없음 -> db로 가서 가지고 옴!


//            String query = "select m from Member m where m.memberType = hellojpa.jpql.MemberType.ADMIN";
//            List<Member> resultList = em.createQuery(query, Member.class).getResultList();
//
//            for (Member member1 : resultList) {
//                System.out.println("Admin인 회원 : " + member1);
//            }
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            em.close();
        }
        emf.close();
    }


}
