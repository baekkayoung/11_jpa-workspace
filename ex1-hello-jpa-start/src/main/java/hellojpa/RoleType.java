package hellojpa;

// enum 열거형 클래스
public enum RoleType {
    GUEST, USER, ADMIN
    // 장점 : 이 프로젝트의 회원을 한 눈에 볼 수 있음, 제한 가능
    
    // DB에 ENUM 이라는 타입이 없음.. 비슷한ㄱ ㅔ있긴한데 일단 없다고 치고
    // @Enumerated를 쓰면 됨
}
