package jpabook.jpashop1.service;

import jpabook.jpashop1.domain.Member;
import jpabook.jpashop1.repository.MemberRepository2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberService2Test {

    @Autowired MemberService2 memberService2;
    @Autowired MemberRepository2 memberRepository2;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService2.join(member);

        //then
        assertEquals(member,memberRepository2.findOne(saveId),"멤버가 잘 저장되었는지 확인");

    }
    
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        
        //when
        memberService2.join(member1);

        /*try {
            memberService2.join(member2); // 예외가 발생!
        } catch (IllegalStateException e) {
            return;
        }*/
        assertThrows(IllegalStateException.class, ()-> memberService2.join(member2));

        //then
        // fail("예외가 발생하지 않았습니다.");
    }
}