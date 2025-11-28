package jpabook.jpashop1.repository;

import jpabook.jpashop1.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // select m from Member m where m.name = ? 을 자동으로 해줌
    List<Member> findByName(String name);

}