package jpabook.jpashop1.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop1.domain.Member;
import jpabook.jpashop1.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    // 1 냅다 멤버 리스트 반환
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    // 2 멤버 리스트 data로 싸서 반환
    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> members = memberService.findMembers(); // 뽑고 싶은 것만 .. => DTO

        List<MemberDTO> collect = members.stream()
                .map(m -> new MemberDTO(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(),collect); // collect에는 이름만 있음. data로 싸는 형태. result로 싸서 만들면 리스트가 data 박스안에 싸짐.
    }
    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data; // 박스를 만든 것. 데이터라는 박스 안에 잘 매핑
    }
    @Data
    @AllArgsConstructor
    static class MemberDTO{
        private String name; // 이름만!
    }

    /// ver1 멤버 저장
    @PostMapping("/api/v1/members")
    public CreateMemberResponse  saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /// ver2 멤버 저장
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id); // 객체 생성
    }

    // id로 객체 생성
    @Data
    static class CreateMemberResponse  {
        private Long id;

        public CreateMemberResponse (Long id) {
            this.id = id;
        }

    }

    // DTO
    @Data
    static class CreateMemberRequest{
        private String name;
    }


    /// ver2 멤버 업데이트
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){
        
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id); // findOne으로 다시 한 번 확실하게 찾음 -> 영속 상태로 이어줌
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

}
