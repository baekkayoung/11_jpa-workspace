package jpabook.jpashop1.service;

import jpabook.jpashop1.domain.Member;
import jpabook.jpashop1.repository.MemberRepository;
import jpabook.jpashop1.repository.MemberRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService2 {

//    @Autowired 얘를 아래로 하면 이제 얘는 바뀔 일 없다 -> final -> 근데 Arg넣으면 만들필요가없다->근데Require로 하면
    private final MemberRepository2 memberRepository2;

    /* @RequiredArgsConstructor 얘가 있으면 얘 없애도 됨
    @Autowired
    public MemberService2(MemberRepository2 memberRepository2) {
        this.memberRepository2 = memberRepository2;
    }*/

    @Transactional
    public Long join(Member member){
        // 중복된 이름 거르기
        validateDuplicateMember(member);
        return memberRepository2.save(member);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository2.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    private List<Member> findMembers(){
        return memberRepository2.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository2.findOne(memberId);
    }

}
