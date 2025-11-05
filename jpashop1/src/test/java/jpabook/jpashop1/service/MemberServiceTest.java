package jpabook.jpashop1.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop1.domain.Member;
import jpabook.jpashop1.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Autowired EntityManager em;

    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");
        
        //when
        Long saveId = memberService.join(member);

        //then
//        assertThat()
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId)); // 위 멤버랑 디비에서 받아온 멤버 같은지 확인
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        /*memberService.join(member1);
        // memberService.join(member2); // 예외가 발생해야함 : 동명이인
        try {
            memberService.join(member2);
        }catch (IllegalStateException e){
            return;
        }*/

        // member1은 저장하고 member2를 저장할 때 터지는 거
        memberService.join(member1);

        // assertThrows(예외클래스, 람다식)
        assertThrows(IllegalStateException.class,
                        ()-> memberService.join(member2));

        //then
//        fail("예외가 발생해야 한다");
    }
}