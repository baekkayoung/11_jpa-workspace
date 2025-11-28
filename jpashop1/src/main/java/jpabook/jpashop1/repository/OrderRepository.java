package jpabook.jpashop1.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpashop1.domain.*;
import jpabook.jpashop1.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }



    public List<Order> findAllByString(OrderSearch orderSearch) {
        //language=JPQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000); //최대 1000건

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }


    public List<Order> findAllByCriteria(OrderSearch orderSearch) {

        CriteriaBuilder cb = em.getCriteriaBuilder(); // 빌더를 찾아
        CriteriaQuery<Order> cq = cb.createQuery(Order.class); // 빌더로 쿼리를 만들기 시작..
        Root<Order> o = cq.from(Order.class); // 어떤 엔티티를 프롬절에 쓸건지?
        Join<Order, Member> m = o.join("member", JoinType.INNER); // 회원과 이너조인을 하겠다.

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        List<Order> result = em.createQuery(
                "select o from Order o"
                        + " join fetch o.member m" // join 패치 일때는 바로
                        + " join fetch o.delivery d", Order.class
        ).getResultList();
        return result;
    }


    //////////////////////// 위에 것 쿼리 dsl 사용해서 변경 //////////////////////////////////
    
    public List<Order> findALl(OrderSearch orderSearch) {
        QOrder order = QOrder.order; // Q파일에서 order를 꺼내는 것.
        QMember member = QMember.member;

        JPAQueryFactory query = new JPAQueryFactory(em); // 쿼리를 짜겠다.
        List<Order> result = query
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus()),nameLike(orderSearch,member))
                .limit(1000)
                .fetch();

        return result;
    }

    // 이름
    private BooleanExpression nameLike(OrderSearch orderSearch, QMember member) {
        if(!StringUtils.hasText(orderSearch.getMemberName())) { // 유효한지 유효하지 않은지 검정 기능
            return null;
        }
        // like "%김%";
        return member.name.contains(orderSearch.getMemberName()); // Like 검색
    }


    // 상태
    private BooleanExpression statusEq(OrderStatus statusCond){
        // 검색할 때 주문의 상티를 선택 안 한 경우 => where절 무시
        if(statusCond == null){
            return null;
        }

        return QOrder.order.status.eq(statusCond);
    }



    public List<Order> findAllWithItem() {
        return  em.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m"+
                        " join fetch o.delivery d"+
                        " join fetch o.orderItems oi"+ // 컬렉션
                        " join fetch oi.item i" , Order.class)
                .setFirstResult(1)
                .setMaxResults(100)
                .getResultList();
    }

    // 먼저 ToOne의 관계를 패치조인 한다. (페이징!)
    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        List<Order> result = em.createQuery(
                "select o from Order o"
                        + " join fetch o.member m" // join 패치 일때는 바로
                        + " join fetch o.delivery d", Order.class
              )
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return result;

    }

}
