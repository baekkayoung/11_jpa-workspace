package jpabook.jpashop1.service;

import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop1.domain.Member;
import jpabook.jpashop1.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@AllArgsConstructor
@Service // 얘도 컴포넌트 포함
@Transactional(readOnly = true) // 조회성 . 얘를 해야 성능이 좋음
//@RequiredArgsConstructor // final 붙은 거만 생성자 만들더줌
public class MemberService {

    // @Autowired // new ~ 이런 거 안해도 스프링이 알아서 자동으로 주입
    private final MemberRepository memberRepository;

//    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    } // @AllArgsConstructor

//    @Autowired // 한 번 호출 멤버서비스만들어질때 자동으로 낑겨넣어줌
    public void setMr(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;

    }

    /*
        회원 가입
        @param member
        @return
        */
    @Transactional // readonly 잠시 해제
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 어차피 여기에서만 쓸거니까 private 해도 됨
    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
    * 회원 전체 조회
    * @return
    * */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long memberId, @NotEmpty String name) {
        Member member = memberRepository.findById(memberId).get();
        member.setName(name);
    }
}
