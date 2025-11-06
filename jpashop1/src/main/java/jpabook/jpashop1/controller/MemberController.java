package jpabook.jpashop1.controller;

import jakarta.validation.Valid;
import jpabook.jpashop1.domain.Address;
import jpabook.jpashop1.domain.Member;
import jpabook.jpashop1.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor // Service
public class MemberController {

    private final MemberService memberService;

    // 회원 가입 페이지
    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm()); // th:object, MemberForm.java
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){ // 여기에 입력한 게 담김, binding한 결과게 result에 담김

        if (result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(),form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        // 이렇게 하는 것 보다 비즈니스 로직을 만들어서 사용하는 게 좋은 코드임! 리팩토링 해보기~

        memberService.join(member);

        return "redirect:/"; // main으로
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";

    }


}
