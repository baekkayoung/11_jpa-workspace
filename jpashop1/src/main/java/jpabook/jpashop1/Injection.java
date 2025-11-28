package jpabook.jpashop1;

import jpabook.jpashop1.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

// DI(Dependency Injection)
public class Injection {


    /*
        1. 필드 주입 : @Autowired를 통해 바로 필드에 의존성이 주입
                      간단하지만 테스트나 유지보수에 불편함
        2. setter 주입 : @Autowired 된 세터 메서드 호출! 선택 가능!
                        다른 곳에서 변경이 되버릴 수 있다.
        3. 생성자 주입 : 밑에서 멤버 레포 정해주면 그걸로 감. 스프링이 생성자 호출 시점에 의존성 주입
                        final 키워드를 사용가능해서 불변성 보장, 테스트 용이성 최고! 내가 repo new로 만들면 됨
     */

    @Autowired
    private MemberRepository mr;
    
    public static void main(String[] args){

    }
}
