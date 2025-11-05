package jpabook.jpashop1.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop1.domain.Member;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@AllArgsConstructor
@RequiredArgsConstructor
public class MemberRepository2 {

//    @PersistenceContext
//    @Autowired
    private final EntityManager em;

    /*
    @Autowired
    public MemberRepository2(EntityManager em) {
        this.em = em;
    }*/

    public Long save(Member member){
       em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name =: name")
                .setParameter("name",name)
                .getResultList();
    }
}
