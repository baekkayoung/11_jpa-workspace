package jpabook.jpashop1.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop1.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 컴포넌트 어노테이션 포함됨 : bean 스캐닝 -> bean에 등록
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext
    // @Autowired -> 로 해도 주입이 됨 근데 그냥 차라리 final 붙이고 @RequiredArg 쓰면 됨
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m" , Member.class).getResultList();
    }

    // 동명이인 있을 수 있으니까 List
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name =: name")
                .setParameter("name",name)
                .getResultList();
    }
}
