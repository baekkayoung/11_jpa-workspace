package jpabook.jpashop1.study;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.transform.Result;

public class GenericWrapper {
    /*
    * Result<T> : 제너릭 래퍼
    *
    * 왜 제너릭 래퍼를 쓰는가?
    * API 응답을 줄 때 단순히 List<Member>를 반환하면 나중에 다른 필드를 넣기가 어려움
    *
    * 예를 들어 이름을 반환한다고 생각해보자
    * [
    *   {"name": "차은우"},
    *   {"name": "박정민"}
    * ]
    *
    * 근데 요구사항이 변경돼서 만약 count를 추가해야 한다면?
    *
    * [
    *   "count" : 2,
    *   "data" :[
    *           {"name": "차은우"},
    *           {"name": "박정민"}
    *           ]
    * ] 이게 좋은 api
    *
    * 이런 식으로 응답 전체를 감사는 박스! Wrapper가 필요함
    * 그게 바로 Result<T>임!
    * t: 어떤 타입이든 가능하다는 의미
    * 즉, API 응답을 유연하게 감싸기 위한 wrapper 클래스이다.
    *
    * */

    @GetMapping("/test/result")
    public Result<String> testResult(){
        // "hello" 라는 문자열을 응답으로 보내고 싶음
        // 근데 바로 hello라는 문자열을 리턴하지 않고
        // Result라는 박스로 한 번 감싸서 응답해보자!
        return new Result<>("hello");
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
